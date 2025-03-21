import React from "react";

const DoctorProfile = () => {
    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold mb-4">Edit Profile</h2>
            <form className="space-y-4 max-w-lg">
                <div>
                    <label className="block font-medium text-gray-700">Name</label>
                    <input
                        type="text"
                        className="w-full border border-gray-300 rounded p-2 mt-1"
                        placeholder="Enter your name"
                    />
                </div>

                <div>
                    <label className="block font-medium text-gray-700">Specialty</label>
                    <input
                        type="text"
                        className="w-full border border-gray-300 rounded p-2 mt-1"
                        placeholder="Enter your specialty"
                    />
                </div>

                <div>
                    <label className="block font-medium text-gray-700">Bio</label>
                    <textarea
                        className="w-full border border-gray-300 rounded p-2 mt-1"
                        rows="4"
                        placeholder="Short bio about you"
                    />
                </div>

                <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
                    Save
                </button>
            </form>
        </div>
    );
};

export default DoctorProfile;
