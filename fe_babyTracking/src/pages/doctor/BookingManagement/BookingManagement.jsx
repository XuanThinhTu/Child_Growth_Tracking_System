import React from "react";

const BookingManagement = () => {
    // Mock data ví dụ
    const bookings = [
        {
            id: 1,
            parentName: "John Doe",
            status: "Pending",
            requestedDate: "2025-03-10 09:00",
        },
        {
            id: 2,
            parentName: "Alice",
            status: "Accepted",
            requestedDate: "2025-03-12 10:30",
        },
    ];

    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold mb-4">Booking Management</h2>
            <table className="w-full border-collapse">
                <thead>
                    <tr className="bg-gray-100 border-b">
                        <th className="text-left p-2">Parent</th>
                        <th className="text-left p-2">Requested Time</th>
                        <th className="text-left p-2">Status</th>
                        <th className="p-2"></th>
                    </tr>
                </thead>
                <tbody>
                    {bookings.map((bk) => (
                        <tr key={bk.id} className="border-b hover:bg-gray-50">
                            <td className="p-2">{bk.parentName}</td>
                            <td className="p-2">{bk.requestedDate}</td>
                            <td className="p-2">
                                <span
                                    className={`px-2 py-1 rounded text-white ${bk.status === "Pending" ? "bg-yellow-500" : "bg-green-600"
                                        }`}
                                >
                                    {bk.status}
                                </span>
                            </td>
                            <td className="p-2">
                                {bk.status === "Pending" && (
                                    <button className="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700">
                                        Accept
                                    </button>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BookingManagement;
