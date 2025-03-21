import React, { useState } from "react";
import { PlusIcon, MinusIcon } from "@heroicons/react/outline";
import Breadcrumbs from "../../../components/elements/Breadcrumb";

export default function FAQPage() {
    // Demo FAQ
    const faqItems = [
        {
            question:
                "Neuropsychology for individuals with neurological, medical and psychological conditions.",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit. In posuere interdum nunc, ornare at. Nunc consectetur augue vitae risus quis. In quam mauris, congue in quis nisl malesuada sapien. Nullam at lectus, ullamcorper sed nibh porta, vulputate. Nulla iaculis sem at egestas efficitur mollis. Mauris tempor lectus urna, id pharetra leo dictum at.`,
        },
        {
            question:
                "Our comprehensive network of specialized services for individuals with neurological, medical and psychological conditions.",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis dui.`,
        },
        {
            question: "Nationally ranked stroke care.",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
        {
            question: "Expect expert, innovative care with a personal touch.",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
        {
            question:
                "Our goal is to be your partner and help support your child's health needs from birth to young adulthood.",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
        {
            question: "Preventing getting sick",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
        {
            question: "Health care hospitals",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
        {
            question:
                "We are your partner and help support your child's health needs from birth to adulthood",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
        {
            question: "How to avoid cold and cough",
            answer: `Lorem ipsum dolor sit amet, consectetur adipiscing elit.`,
        },
    ];

    // State: item đang mở
    const [openIndex, setOpenIndex] = useState(null);

    const toggleFAQ = (index) => {
        setOpenIndex(openIndex === index ? null : index);
    };

    // Form state
    const [message, setMessage] = useState("");
    const [userName, setUserName] = useState("");
    const [userEmail, setUserEmail] = useState("");
    const [saveInfo, setSaveInfo] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        // Tạm demo alert
        alert(`Message: ${message}\nName: ${userName}\nEmail: ${userEmail}\nSaveInfo: ${saveInfo}`);
        // Reset
        setMessage("");
        setUserName("");
        setUserEmail("");
        setSaveInfo(false);
    };

    return (
        <>
            <Breadcrumbs headline="FAQ" />
            <div className="max-w-3xl mx-auto px-4 py-8">
                {/* Heading */}
                <div className="text-center mb-10">
                    <h1 className="text-3xl font-bold text-gray-800 sm:text-4xl">
                        Frequently Asked Questions
                    </h1>
                    <p className="mt-2 text-gray-600">
                        Find answers to common questions below. Still need help? Ask your own!
                    </p>
                </div>

                {/* FAQ list */}
                <div className="divide-y divide-gray-200">
                    {faqItems.map((item, index) => {
                        const isOpen = openIndex === index;
                        return (
                            <div key={index} className="py-4">
                                <button
                                    onClick={() => toggleFAQ(index)}
                                    className="w-full text-left"
                                >
                                    <div className="grid grid-cols-[auto,1fr] gap-2 items-start">
                                        {/* Icon + / - */}
                                        <div className="w-5 h-5 flex-shrink-0 text-gray-600">
                                            {isOpen ? <MinusIcon /> : <PlusIcon />}
                                        </div>
                                        {/* Question */}
                                        <div className="font-semibold text-gray-900 leading-snug">
                                            {item.question}
                                        </div>
                                    </div>
                                </button>
                                {/* Câu trả lời, chỉ hiển thị nếu open */}
                                {isOpen && item.answer && (
                                    <div className="mt-2 ml-7 text-gray-700 text-sm leading-relaxed">
                                        {item.answer}
                                    </div>
                                )}
                            </div>
                        );
                    })}
                </div>

                {/* Leave a reply form */}
                <div className="border-t border-gray-200 pt-8 mt-8">
                    <h3 className="text-2xl font-bold text-gray-800 mb-4">Leave a reply</h3>
                    <p className="text-sm text-gray-500 mb-4">
                        Your email address will not be published. Required fields are marked *
                    </p>
                    <form onSubmit={handleSubmit} className="space-y-6">
                        {/* Message */}
                        <div>
                            <label
                                htmlFor="message"
                                className="block text-sm font-medium text-gray-700 mb-1"
                            >
                                Message *
                            </label>
                            <textarea
                                id="message"
                                name="message"
                                className="w-full border border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-purple-500"
                                rows={4}
                                value={message}
                                onChange={(e) => setMessage(e.target.value)}
                            />
                        </div>

                        {/* Submit button */}
                        <div className="text-right">
                            <button
                                type="submit"
                                className="inline-flex items-center gap-2 bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700 transition"
                            >
                                SEND NOW
                                <PlusIcon className="h-4 w-4" />
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}
