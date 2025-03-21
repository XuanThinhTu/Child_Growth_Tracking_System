import React, { useState, useEffect } from 'react';
import { createBlog, getAllCategories } from '../../../services/APIServices';
import toast from 'react-hot-toast';

const BlogCreationForm = ({ onPublish }) => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [featuredImage, setFeaturedImage] = useState(null);
    const [selectedCategory, setSelectedCategory] = useState(''); // Lưu ID category

    const [categories, setCategories] = useState([]); // Danh sách category
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    // Lấy danh sách category khi form mount
    useEffect(() => {
        (async () => {
            try {
                const res = await getAllCategories();
                if (res.success) {
                    setCategories(res.data); // res.data là mảng categories
                } else {
                    setError(res.message || 'Không thể lấy danh sách danh mục');
                }
            } catch (err) {
                setError(err.message || 'Lỗi khi gọi API lấy danh mục');
            }
        })();
    }, []);


    const handlePublish = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        // Chuẩn bị payload cho API
        const blogPayload = {
            title,
            content,
            categoryId: selectedCategory ? parseInt(selectedCategory) : null
        };

        const formData = new FormData();
        formData.append("title", title)
        formData.append("content", content)
        formData.append("categoryId", parseInt(selectedCategory))
        if (featuredImage) {
            formData.append("images", featuredImage)
        }
        try {
            const result = await createBlog(formData);
            if (result) {
                // Gọi callback để cập nhật danh sách blog
                onPublish(result.data);
                // Reset form
                setTitle('');
                setContent('');
                setFeaturedImage(null);
                setSelectedCategory('');
                toast.success("Blog created successfully!")
            } else {
                toast.error('Blog creation failed');
            }
        } catch (err) {
            setError(err.message || 'Có lỗi xảy ra khi tạo bài viết.');
        }
        setLoading(false);
    };

    return (
        <div className="p-4 bg-white rounded shadow mb-8">
            <h2 className="text-2xl font-bold mb-4">Create Blog</h2>
            {error && <p className="text-red-600 mb-4">{error}</p>}

            <form className="space-y-4" onSubmit={handlePublish}>
                {/* Title */}
                <div>
                    <label className="block font-medium text-gray-700">Title</label>
                    <input
                        type="text"
                        className="w-full border border-gray-300 rounded p-2 mt-1"
                        placeholder="Blog Title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>

                {/* Featured Image */}
                <div>
                    <label className="block font-medium text-gray-700">Featured Image</label>
                    <input
                        type="file"
                        className="mt-1"
                        onChange={(e) => setFeaturedImage(e.target.files[0])}
                    />
                </div>

                {/* Category */}
                <div>
                    <label className="block font-medium text-gray-700">Category</label>
                    <select
                        className="w-full border border-gray-300 rounded p-2 mt-1"
                        value={selectedCategory}
                        onChange={(e) => setSelectedCategory(e.target.value)}
                        required
                    >
                        <option value="">-- Select Category --</option>
                        {categories.map((cat) => (
                            <option key={cat.id} value={cat.id}>
                                {cat.title}
                            </option>
                        ))}
                    </select>
                </div>

                {/* Content */}
                <div>
                    <label className="block font-medium text-gray-700">Content</label>
                    <textarea
                        className="w-full border border-gray-300 rounded p-2 mt-1"
                        rows="6"
                        placeholder="Write your blog content here..."
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                    />
                </div>

                {/* Submit */}
                <button
                    type="submit"
                    className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                    disabled={loading}
                >
                    {loading ? 'Publishing...' : 'Publish'}
                </button>
            </form>
        </div>
    );
};

export default BlogCreationForm;
