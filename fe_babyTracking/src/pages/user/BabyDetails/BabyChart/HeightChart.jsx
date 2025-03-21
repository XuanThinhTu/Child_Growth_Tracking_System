import { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  ResponsiveContainer,
  CartesianGrid,
  Tooltip,
} from "recharts";
import {
  getBabyGrowthData,
  getBabyInfo,
  getBoyStandardIndex,
  getGirlStandardIndex,
  getPredictGrowthData,
} from "../../../../services/APIServices";
import dayjs from "dayjs";

const HeightChart = ({ babyId }) => {
  const [baby, setBaby] = useState(null);
  const [growthData, setGrowthData] = useState([]); // Dữ liệu chuẩn (SD lines)
  const [userData, setUserData] = useState([]); // Dữ liệu bé
  const [isFullScreen, setIsFullScreen] = useState(false);
  const [predictData, setPredictData] = useState([]);

  // Hàm tính số ngày từ ngày sinh
  const calculateDays = (birthDate, measuredAt) => {
    const birth = dayjs(birthDate);
    const measured = dayjs(measuredAt);
    return measured.diff(birth, "day");
  };
  // Hàm tìm dòng dữ liệu chuẩn gần nhất với ngày target
  const getClosestStandardData = (targetDay) =>
    growthData.reduce((closest, current) =>
      Math.abs(current.day - targetDay) < Math.abs(closest.day - targetDay)
        ? current
        : closest
    );

  // Hàm lấy mảng các chỉ số chuẩn từ dữ liệu chuẩn
  const getStandardValues = (data) => [
    data.SD4neg,
    data.SD3neg,
    data.SD2neg,
    data.SD1neg,
    data.SD0,
    data.SD1,
    data.SD2,
    data.SD3,
    data.SD4,
  ];
  useEffect(() => {
    const fetchBabyInfo = async () => {
      try {
        const result = await getBabyInfo(babyId);
        setBaby(result);
      } catch (error) {
        console.log(error);
      }
    };
    fetchBabyInfo();
  }, [babyId]);

  // Lấy dữ liệu thực của bé
  useEffect(() => {
    const fetchGrowthData = async () => {
      if (!baby) return;
      try {
        const result = await getBabyGrowthData(babyId);
        const formatted = result.map((item) => ({
          day: calculateDays(baby.birthDate, item.measuredAt),
          height: item.height,
          weight: item.weight,
        }));
        setUserData(formatted);
      } catch (error) {
        console.log(error);
      }
    };
    fetchGrowthData();
  }, [baby, babyId]);

  useEffect(() => {
    const fetchPredictData = async () => {
      if (!baby || !userData.length) return;
      try {
        const result = await getPredictGrowthData(babyId);
        const formattedData = result.map((item) => ({
          day: calculateDays(baby.birthDate, item.predictedDate),
          predictHeight: item.predictedHeight,
        }));
        // Lấy dữ liệu của ngày cuối cùng của bé và chuyển đổi sang object có 2 thuộc tính: day và predictHeight
        const lastDayData = userData[userData.length - 1];
        const lastDayPredict = {
          day: lastDayData.day,
          predictHeight: lastDayData.height,
        };

        setPredictData([lastDayPredict, ...formattedData]);
      } catch (error) {
        console.log(error);
      }
    };
    fetchPredictData();
  }, [baby, babyId, userData]);

  // Lấy dữ liệu chuẩn
  useEffect(() => {
    const fetchHeightData = async () => {
      if (!baby) return;
      try {
        const result =
          baby.gender === "Boy"
            ? await getBoyStandardIndex()
            : await getGirlStandardIndex();

        const formatted = result?.map((item) => ({
          day: item.periodType === "DAY" ? item.period : item.period * 30 + 56, // ngày
          SD4neg: item.heightNeg4Sd,
          SD3neg: item.heightNeg3Sd,
          SD2neg: item.heightNeg2Sd,
          SD1neg: item.heightNeg1Sd,
          SD0: item.heightMedian,
          SD1: item.heightPos1Sd,
          SD2: item.heightPos2Sd,
          SD3: item.heightPos3Sd,
          SD4: item.heightPos4Sd,
        }));
        setGrowthData(formatted);
      } catch (error) {
        console.log(error);
      }
    };
    fetchHeightData();
  }, [baby]);

  // === Tính domain X ===
  // Lấy ngày lớn nhất của bé + 60

  const userMaxDay = userData.length
    ? Math.max(...userData.map((d) => d.day))
    : Math.max(...growthData.map((d) => d.day));

  const domainMax = userMaxDay + 60; // Dư 60 ngày

  // Tạo mảng tick bội số 30 => hiển thị "tháng"
  const ticks = [];
  let period = userData.length ? 30 : 365;
  for (let i = period; i <= domainMax; i += period) {
    ticks.push(i);
  }

  // === Tính domain Y “center” quanh dữ liệu bé (bỏ qua SD lines) ===
  // Tính domain Y dựa trên so sánh giữa dữ liệu của bé và chỉ số chuẩn
  let yMin = 0;
  let yMax = 130; // fallback

  if (userData.length && growthData.length) {
    const babyHeights = userData.map((d) => d.height);
    const babyMin = Math.min(...babyHeights);
    const babyMax = Math.max(...babyHeights);

    const babyMinDay = userData.find((d) => d.height === babyMin).day;
    const babyMaxDay = userData.find((d) => d.height === babyMax).day;

    const closestStandardMin = getClosestStandardData(babyMinDay);
    const closestStandardMax = getClosestStandardData(babyMaxDay);

    const standardMin = Math.min(...getStandardValues(closestStandardMin));
    const standardMax = Math.max(...getStandardValues(closestStandardMax));

    yMin = Math.min(babyMin, standardMin);
    yMax = Math.max(babyMax, standardMax);
    if (yMin < 0) yMin = 0;
  }

  // Render chart
  const renderChart = () => (
    <ResponsiveContainer width="100%" height="100%">
      <LineChart data={growthData} margin={{ right: 20 }}>
        <CartesianGrid stroke="#ccc" strokeDasharray="" />

        {/* Trục X */}
        <XAxis
          dataKey="day"
          type="number"
          domain={[0, domainMax]}
          scale="linear"
          ticks={ticks}
          interval={0}
          tickFormatter={(val) =>
            userData.length ? `${val / 30}` : `${val / 365}`
          }
          label={{
            value: userData.length ? "Tháng" : "Năm",
            position: "insideBottomRight",
            offset: 0,
          }}
        />

        {/* Trục Y */}
        <YAxis
          domain={[yMin, yMax]}
          label={{ value: "cm", angle: -90, position: "insideLeft" }}
        />

        {/* Tooltip */}
        <Tooltip
          labelFormatter={(dayValue) => `Ngày: ${dayValue}`}
          formatter={(value, name) => {
            if (name === "height") {
              return [`${value} cm`, "Chiều cao Bé"];
            }
            // SD lines => hiển thị raw
            return [value, name];
          }}
        />

        {/* Đường SD */}
        {growthData.length > 0 &&
          Object.keys(growthData[0])
            .filter((key) => key !== "day")
            .map((key, index) => (
              <Line
                key={key}
                type="monotone"
                dataKey={key}
                stroke={index < 3 ? "#000" : index < 6 ? "#f00" : "#0a0"}
                strokeDasharray={index === 4 ? "5 5" : ""}
                activeDot={false}
                dot={false}
              />
            ))}

        {/* Đường dữ liệu bé */}
        {userData.length > 0 && (
          <Line
            type="monotone"
            dataKey="height"
            data={userData}
            stroke="#007bff"
            dot={{ r: 4 }}
            activeDot={{ r: 6 }}
            isAnimationActive={false}
          />
        )}

        {predictData.length > 0 && (
          <Line
            type="monotone"
            dataKey="predictHeight"
            data={predictData}
            stroke="gray"
            dot={{ r: 4 }}
            activeDot={{ r: 6 }}
            isAnimationActive={false}
          />
        )}
      </LineChart>
    </ResponsiveContainer>
  );

  return (
    <div>
      <div className="w-full px-4 py-12">
        <div className="flex justify-between items-center mb-6">
          <h3 className="text-2xl font-bold">Chiều cao</h3>
          <a
            href="#"
            className="text-blue-500 text-lg hover:underline"
            onClick={(e) => {
              e.preventDefault();
              setIsFullScreen(true);
            }}
          >
            Chỉ số tiêu chuẩn
          </a>
        </div>

        {/* Chart */}
        <div style={{ width: "100%", height: 600 }}>{renderChart()}</div>

        <div className="flex justify-center items-center mt-6 text-lg text-purple-500">
          <a
            href="#"
            className="hover:underline flex items-center"
            onClick={(e) => {
              e.preventDefault();
              setIsFullScreen(true);
            }}
          >
            Xem chi tiết <span className="ml-1">&gt;</span>
          </a>
          <span className="mx-4 border-l border-gray-300 h-5"></span>
          <a
            href="#"
            className="hover:underline flex items-center"
            onClick={(e) => {
              e.preventDefault();
              setIsFullScreen(true);
            }}
          >
            Xem toàn màn hình <span className="ml-1">&gt;</span>
          </a>
        </div>
      </div>

      {/* Modal toàn màn hình */}
      {isFullScreen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 z-50">
          <div className="bg-white w-full h-full flex flex-col">
            <div className="relative border-b p-4 flex items-center justify-center">
              <span className="font-bold text-lg">Biểu đồ Chiều Cao</span>
              <button
                className="absolute right-4 text-gray-600 hover:text-gray-800"
                onClick={() => setIsFullScreen(false)}
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-6 w-6 fill-current"
                  viewBox="0 0 20 20"
                >
                  <path
                    fillRule="evenodd"
                    d="M10 9.293l4.646-4.647a.5.5 0 01.708.708L10.707 
                      10l4.647 4.646a.5.5 0 01-.708.708L10 10.707l-4.646 
                      4.647a.5.5 0 01-.708-.708L9.293 10 4.646 
                      5.354a.5.5 0 01.708-.708L10 9.293z"
                    clipRule="evenodd"
                  />
                </svg>
              </button>
            </div>
            <div className="flex-1 p-4">{renderChart()}</div>
          </div>
        </div>
      )}
    </div>
  );
};

export default HeightChart;
