import React, { useState, useEffect } from 'react';
import BlogCreationForm from './BlogCreationForm';
import { getAllBlogs } from '../../../services/APIServices';

const BlogCreation = () => {
    const [blogs, setBlogs] = useState([]);          // Danh sách blog từ API
    const [editingBlog, setEditingBlog] = useState(null);

    // Lấy danh sách blog khi component mount
    useEffect(() => {
        const fetchBlogs = async () => {
            try {
                const res = await getAllBlogs();
                // Giả định server trả về { success, data: { content: [...] } }
                if (res.success) {
                    // Lưu ý: res.data.content là mảng blog
                    // Mỗi blog có thể có blogImages, authorId, categoryId...
                    setBlogs(res.data.content || []);
                } else {
                    console.error(res.message || 'Không thể lấy danh sách blog');
                }
            } catch (err) {
                console.error('Lỗi khi gọi API lấy danh sách blog:', err);
            }
        };
        fetchBlogs();
    }, []);

    // Khi tạo blog thành công
    const handlePublish = (newBlog) => {
        // newBlog là dữ liệu trả về từ API createBlog
        // Đưa blog mới lên đầu danh sách
        setBlogs((prev) => [newBlog, ...prev]);
    };

    // Xóa blog khỏi state (demo, chưa gọi API xóa)
    const handleDelete = (id) => {
        if (window.confirm('Are you sure you want to delete this blog?')) {
            setBlogs((prev) => prev.filter((blog) => blog.id !== id));
        }
    };

    // Cập nhật blog trong state (demo, chưa gọi API update)
    const handleUpdate = (updatedBlog) => {
        setBlogs((prev) =>
            prev.map((blog) => (blog.id === updatedBlog.id ? updatedBlog : blog))
        );
        setEditingBlog(null);
    };

    return (
        <div className="p-4">
            {/* Form Tạo Blog */}
            <BlogCreationForm onPublish={handlePublish} />

            {/* Danh sách blog của Doctor */}
            <h2 className="text-2xl font-bold mb-4">Your Blogs</h2>
            <div className="space-y-8">
                {blogs.map((blog) => (
                    <div key={blog.id} className="p-4 bg-white rounded shadow">
                        <div className="flex items-center justify-between mb-4">
                            <h3 className="text-xl font-bold">{blog.title}</h3>
                            <div className="space-x-2">
                                <button
                                    onClick={() => setEditingBlog(blog)}
                                    className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
                                >
                                    Edit
                                </button>
                                <button
                                    onClick={() => handleDelete(blog.id)}
                                    className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                        {/* Hiển thị ảnh đầu tiên nếu blogImages không rỗng */}
                        {blog.blogImages && blog.blogImages.length > 0 && (
                            <img
                                src={blog.blogImages[0].url}
                                alt={blog.title}
                                className="w-full h-64 object-cover rounded mb-4"
                            />
                        )}
                        <p className="text-gray-600 text-sm mb-2">
                            {new Date(blog.createdAt).toLocaleDateString()}
                        </p>
                        <p className="text-gray-700">
                            {blog.content.substring(0, 200)}...
                        </p>
                    </div>
                ))}
            </div>

            {/* Form Chỉnh Sửa Blog (hiển thị khi có bài cần chỉnh sửa) */}
            {editingBlog && (
                <div>
                    {/* BlogEditForm component ở đây, nếu bạn muốn cho phép update */}
                </div>
            )}
        </div>
    );
};

export default BlogCreation;
