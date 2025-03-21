import React from "react";
import {
    UserIcon,
    ChatAlt2Icon,
    CalendarIcon,
    PencilAltIcon,
} from "@heroicons/react/outline";

export default function DoctorSidebar({ activeTab, setActiveTab }) {
    // Định nghĩa các mục sidebar
    const navItems = [
        { id: "profile", label: "Profile", icon: UserIcon },
        { id: "consultation", label: "Consultation Request", icon: ChatAlt2Icon },
        { id: "booking", label: "Booking Management", icon: CalendarIcon },
        { id: "blog", label: "Blog Creation", icon: PencilAltIcon },
        { id: "workSchedule", label: "Work Schedule", icon: CalendarIcon }
    ];

    return (
        <aside className="w-64 bg-white border-r shadow p-4">
            <nav>
                <ul className="space-y-2">
                    {navItems.map((item) => {
                        const IconComp = item.icon;
                        const isActive = activeTab === item.id;
                        return (
                            <li key={item.id}>
                                <button
                                    onClick={() => setActiveTab(item.id)}
                                    className={`flex items-center space-x-2 px-3 py-2 rounded w-full text-left transition ${isActive ? "bg-blue-50" : "hover:bg-blue-50"
                                        }`}
                                >
                                    <IconComp className="h-5 w-5 text-blue-500" />
                                    <span className="font-medium text-gray-700">
                                        {item.label}
                                    </span>
                                </button>
                            </li>
                        );
                    })}
                </ul>
            </nav>
        </aside>
    );
}
