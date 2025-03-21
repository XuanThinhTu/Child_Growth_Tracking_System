import React, { useState } from "react";

const BMICalculator = () => {
    // State điều khiển form nhập
    const [sex, setSex] = useState("boy");
    const [ageType, setAgeType] = useState("yearsMonths");
    const [years, setYears] = useState("");
    const [months, setMonths] = useState("");
    const [height, setHeight] = useState("");
    const [weight, setWeight] = useState("");

    // State hiển thị kết quả
    const [showResult, setShowResult] = useState(false);

    // Tính BMI
    const heightInMeters = parseFloat(height) / 100 || 0;
    const weightInKg = parseFloat(weight) || 0;
    const calculatedBMI = heightInMeters
        ? (weightInKg / (heightInMeters * heightInMeters)).toFixed(1)
        : 0;

    // Phân loại BMI (minh họa)
    const getBMICategory = (bmi) => {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Healthy Weight";
        if (bmi < 30) return "Overweight";
        return "Obese";
    };

    // Tính lượng calo khuyến nghị (giả lập)
    const recommendedCalories = weightInKg > 0 ? (weightInKg * 80).toFixed(0) : 0;

    const handleCalculate = () => {
        setShowResult(true);
    };

    const handleBack = () => {
        setShowResult(false);
    };

    // --- PHẦN HIỂN THỊ KẾT QUẢ ---
    if (showResult) {
        return (
            <div className="w-full">
                {/* Header */}
                <header className="bg-lime-400 text-white p-4">
                    <h2 className="text-2xl font-bold">BMI Calculator for Child and Teen</h2>
                </header>

                <div className="p-6">
                    {/* Nút Back */}
                    <div className="flex justify-end mb-4">
                        <button onClick={handleBack} className="text-lime-700 hover:underline font-medium">
                            Back to Calculator
                        </button>
                    </div>

                    {/* Khối hiển thị kết quả */}
                    <div className="w-full border-2 border-lime-200 bg-lime-50 rounded p-6">
                        <h3 className="text-xl font-semibold mb-4 text-lime-700">Calculated Results</h3>
                        {/* Hàng thông tin BMI */}
                        <div className="flex flex-col md:flex-row items-center justify-around bg-white border border-orange-200 rounded p-4">
                            <div className="text-center mb-2 md:mb-0">
                                <p className="text-2xl font-bold text-lime-700">{calculatedBMI}</p>
                                <p className="text-sm text-gray-500">BMI</p>
                            </div>
                            <div className="text-center mb-2 md:mb-0">
                                <p className="text-xl font-semibold text-lime-800">
                                    {getBMICategory(calculatedBMI)}
                                </p>
                                <p className="text-sm text-gray-500">BMI CATEGORY</p>
                            </div>
                            <div className="text-center">
                                <p className="text-xl font-semibold text-lime-800">{recommendedCalories} kcal</p>
                                <p className="text-sm text-gray-500">RECOMMENDED DAILY CALORIES</p>
                            </div>
                        </div>

                        {/* Thông tin người dùng nhập */}
                        <div className="mt-6 border-t pt-4">
                            <h4 className="font-bold mb-2 text-lime-700">Information Entered</h4>
                            <div className="grid grid-cols-2 gap-4">
                                <div>
                                    <p className="text-gray-700">
                                        <strong>Age: </strong>
                                        {years
                                            ? `${years} year${years > 1 ? "s" : ""} `
                                            : ""}
                                        {months
                                            ? `${months} month${months > 1 ? "s" : ""}`
                                            : ""}
                                    </p>
                                    <p className="text-gray-700">
                                        <strong>Sex: </strong>
                                        {sex === "boy" ? "Boy" : "Girl"}
                                    </p>
                                </div>
                                <div>
                                    <p className="text-gray-700">
                                        <strong>Height: </strong>
                                        {height ? `${height} cm` : "N/A"}
                                    </p>
                                    <p className="text-gray-700">
                                        <strong>Weight: </strong>
                                        {weight ? `${weight} kg` : "N/A"}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    // --- PHẦN HIỂN THỊ FORM NHẬP LIỆU ---
    return (
        <div className="w-full">
            {/* Header */}
            <header className="bg-lime-400 text-white p-4">
                <h2 className="text-2xl font-bold">BMI Calculator for Child and Teen</h2>
            </header>

            {/* Khu vực nội dung chia đôi */}
            <div className="flex">
                {/* Left Form Section */}
                <div className="w-1/2 p-6 border-r bg-lime-50">
                    <div className="grid grid-cols-2 gap-4">
                        {/* SEX + AGE (cột trái) */}
                        <div>
                            {/* SEX */}
                            <div className="mt-2">
                                <label className="block font-semibold text-lime-700">SEX</label>
                                <div className="flex gap-4 mt-1">
                                    <label className="flex items-center">
                                        <input
                                            type="radio"
                                            name="sex"
                                            value="boy"
                                            className="mr-1"
                                            checked={sex === "boy"}
                                            onChange={(e) => setSex(e.target.value)}
                                        />
                                        Boy
                                    </label>
                                    <label className="flex items-center">
                                        <input
                                            type="radio"
                                            name="sex"
                                            value="girl"
                                            className="mr-1"
                                            checked={sex === "girl"}
                                            onChange={(e) => setSex(e.target.value)}
                                        />
                                        Girl
                                    </label>
                                </div>
                            </div>

                            {/* AGE */}
                            <div className="mt-4">
                                <label className="block font-semibold text-lime-700">AGE</label>
                                <div className="flex items-center gap-2 mt-1">
                                    <label className="flex items-center text-sm">
                                        <input
                                            type="radio"
                                            name="ageType"
                                            value="yearsMonths"
                                            className="mr-1"
                                            checked={ageType === "yearsMonths"}
                                            onChange={(e) => setAgeType(e.target.value)}
                                        />
                                        Years, Months
                                    </label>
                                </div>

                                {ageType === "yearsMonths" && (
                                    <div className="flex gap-2 mt-2">
                                        <input
                                            type="number"
                                            min="2"
                                            max="19"
                                            placeholder="years (2-19)"
                                            className="border border-lime-200 rounded p-2 w-1/2"
                                            value={years}
                                            onChange={(e) => setYears(e.target.value)}
                                        />
                                        <input
                                            type="number"
                                            min="0"
                                            max="11"
                                            placeholder="months (0-11)"
                                            className="border border-lime-200 rounded p-2 w-1/2"
                                            value={months}
                                            onChange={(e) => setMonths(e.target.value)}
                                        />
                                    </div>
                                )}
                            </div>
                        </div>

                        {/* HEIGHT + WEIGHT (cột phải) */}
                        <div>
                            {/* HEIGHT */}
                            <div className="mt-2">
                                <label className="block font-semibold text-lime-700">HEIGHT</label>
                                <input
                                    type="number"
                                    placeholder="cm"
                                    className="border border-lime-200 rounded p-2 mt-1 w-full"
                                    value={height}
                                    onChange={(e) => setHeight(e.target.value)}
                                />
                            </div>

                            {/* WEIGHT */}
                            <div className="mt-4">
                                <label className="block font-semibold text-lime-700">
                                    WEIGHT{" "}
                                    <span className="text-gray-500 text-sm">(decimal allowed)</span>
                                </label>
                                <input
                                    type="number"
                                    placeholder="kg"
                                    className="border border-lime-200 rounded p-2 mt-1 w-full"
                                    value={weight}
                                    onChange={(e) => setWeight(e.target.value)}
                                />
                            </div>
                        </div>

                        {/* Nút Calculate (trải dài cả 2 cột) */}
                        <div className="col-span-2 mt-6">
                            <button
                                className="bg-lime-500 text-white font-bold py-2 px-6 rounded w-full hover:bg-lime-600"
                                onClick={handleCalculate}
                            >
                                Calculate
                            </button>
                        </div>
                    </div>
                </div>

                {/* Right BMI Chart Section */}
                <div className="w-1/2 h-full flex flex-col p-6 bg-white">
                    <h2 className="text-4xl font-bold text-gray-800 text-center mb-4 w-full">
                        BMI Calculator Chart
                    </h2>
                    <div className="flex-grow w-full flex flex-col justify-center">
                        <table className="w-full border-collapse">
                            <thead className="bg-gray-100">
                                <tr>
                                    <th className="p-4 text-left font-medium text-gray-700 text-lg">BMI</th>
                                    <th className="p-4 text-left font-medium text-gray-700 text-lg">
                                        Weight status
                                    </th>
                                </tr>
                            </thead>
                            <tbody className="text-gray-700 text-base">
                                <tr className="border-t border-b border-gray-300">
                                    <td className="p-4 font-semibold">Below 18.5</td>
                                    <td className="p-4 text-blue-600">Underweight</td>
                                </tr>
                                <tr className="border-t border-b border-gray-300">
                                    <td className="p-4 font-semibold">18.5 - 24.9</td>
                                    <td className="p-4 text-green-600">Healthy</td>
                                </tr>
                                <tr className="border-t border-b border-gray-300">
                                    <td className="p-4 font-semibold">25.0 - 29.9</td>
                                    <td className="p-4 text-orange-600">Overweight</td>
                                </tr>
                                <tr className="border-t border-b border-gray-300">
                                    <td className="p-4 font-semibold">30.0 - and Above</td>
                                    <td className="p-4 text-red-600">Obese</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <p className="mt-4 text-center text-sm text-gray-500 w-full">
                        * BMR Metabolic Rate / BMI Body Mass Index
                    </p>
                </div>
            </div>
        </div>
    );
};

export default BMICalculator;
