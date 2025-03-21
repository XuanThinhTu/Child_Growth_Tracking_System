import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllBlogs, getAllCategories } from '../../../services/APIServices';

const Blog = () => {
    const [blogs, setBlogs] = useState([]);
    const [categories, setCategories] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchBlogs = async () => {
            try {
                const res = await getAllBlogs();
                if (res.success) {
                    setBlogs(res.data.content || []);
                } else {
                    console.error(res.message || 'Không thể lấy danh sách blog');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API blog:', error);
            }
        };

        const fetchCategories = async () => {
            try {
                const catRes = await getAllCategories();
                if (catRes.success) {
                    setCategories(catRes.data);
                } else {
                    console.error(catRes.message || 'Không thể lấy danh sách danh mục');
                }
            } catch (error) {
                console.error('Lỗi khi gọi API categories:', error);
            }
        };

        fetchBlogs();
        fetchCategories();
    }, []);

    // Lọc blog theo từ khóa (title)
    const filteredBlogs = blogs.filter((blog) =>
        blog.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Related Posts: lấy category của blog đầu tiên, rồi lọc ra các bài cùng category
    let relatedPosts = [];
    if (filteredBlogs.length > 0) {
        const firstCategory = filteredBlogs[0].categoryName;
        relatedPosts = blogs.filter((blog) => blog.categoryName === firstCategory);
    }

    return (
        <div className="container mx-auto p-8">
            <div className="flex flex-col md:flex-row gap-8">
                {/* Left Column (2/3) */}
                <div className="md:w-2/3">
                    {filteredBlogs.map((post) => (
                        <div
                            key={post.id}
                            className="mb-12 bg-white rounded shadow"
                        >
                            {/* Bọc ảnh trong một div để overflow-hidden */}
                            <div className="overflow-hidden">
                                {post.blogImages && post.blogImages.length > 0 && (
                                    <img
                                        src={post.blogImages[0].url}
                                        alt={post.title}
                                        className="w-full h-[500px] object-cover 
                               transition-transform duration-300 ease-in-out 
                               hover:scale-105"
                                    />
                                )}
                            </div>
                            <div className="p-6">
                                <div className="text-gray-600 text-sm mb-2">
                                    {new Date(post.createdAt).toLocaleDateString()} - {post.authorName}
                                </div>
                                <h1 className="text-3xl font-bold mb-4">{post.title}</h1>
                                <p className="text-gray-700 mb-4">
                                    {post.content.substring(0, 150)}...
                                </p>
                                <Link
                                    to={`/blog/${post.id}`}
                                    className="inline-block bg-green-800 text-white py-2 px-4 rounded hover:bg-green-700"
                                >
                                    Read More
                                </Link>
                            </div>
                        </div>
                    ))}
                </div>

                {/* Right Column (Sidebar - 1/3) */}
                <div className="md:w-1/3 space-y-8">
                    {/* Search Bar */}
                    <div className="p-4 bg-white rounded shadow">
                        <h2 className="text-xl font-bold mb-4">Search</h2>
                        <input
                            type="text"
                            placeholder="Tìm kiếm bài viết..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="w-full border border-gray-300 p-2 rounded"
                        />
                    </div>

                    {/* Categories List */}
                    <div className="p-4 bg-white rounded shadow">
                        <h2 className="text-xl font-bold mb-4">Categories</h2>
                        <ul className="space-y-2">
                            {categories.map((cat) => (
                                <li key={cat.id}>
                                    {/* tuỳ logic click category */}
                                    <button className="text-green-800 hover:underline">
                                        {cat.title}
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </div>

                    {/* Related Posts */}
                    <div className="p-4 bg-white rounded shadow">
                        <h2 className="text-xl font-bold mb-4">Related Posts</h2>
                        <ul className="space-y-4">
                            {relatedPosts.map((post) => (
                                <li key={post.id} className="flex gap-4">
                                    {post.blogImages && post.blogImages.length > 0 && (
                                        <img
                                            src={post.blogImages[0].url}
                                            alt={post.title}
                                            className="w-16 h-16 object-cover rounded"
                                        />
                                    )}
                                    <div>
                                        <Link
                                            to={`/blog/${post.id}`}
                                            className="text-green-800 font-semibold hover:underline"
                                        >
                                            {post.title}
                                        </Link>
                                        <div className="text-gray-600 text-xs">
                                            {new Date(post.createdAt).toLocaleDateString()}
                                        </div>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Blog;
