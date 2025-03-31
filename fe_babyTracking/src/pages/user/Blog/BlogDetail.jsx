import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getBlogDetail } from "../../../services/APIServices";

const BlogDetail = () => {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBlogDetail = async () => {
            try {
                const data = await getBlogDetail(id);
                if (data.success) {
                    setPost(data.data);
                } else {
                    throw new Error(data.message || "Không tìm thấy bài viết.");
                }
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchBlogDetail();
    }, [id]);

    if (loading) {
        return <div className="text-center p-8">Đang tải...</div>;
    }

    if (error) {
        return (
            <div className="container mx-auto p-8">
                <h1 className="text-3xl font-bold mb-4">{error}</h1>
                <Link to="/blog" className="text-green-800 hover:underline">&larr; Quay lại blog</Link>
            </div>
        );
    }

    return (
        <div className="container mx-auto p-8">
            <Link to="/blog" className="text-green-800 hover:underline mb-4 inline-block">&larr; Quay lại blog</Link>
            <div className="bg-white rounded shadow overflow-hidden">
                {/* Hiển thị hình ảnh đúng cách */}
                <div className="overflow-hidden">
                    {post.blogImages && post.blogImages.length > 0 && (
                        <img
                            src={post.blogImages[0].url}  
                            alt={post.title}
                            className="w-full h-96 object-cover 
                                       transition-transform duration-300 ease-in-out 
                                       hover:scale-105"
                        />
                    )}
                </div>
                <div className="p-6">
                    <div className="text-gray-600 text-sm mb-2">
                        {new Date(post.createdAt).toLocaleDateString()} - {post.authorName}
                    </div>
                    <h1 className="text-4xl font-bold mb-4">{post.title}</h1>
                    <div className="text-gray-800 leading-relaxed">
                        {post.content}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default BlogDetail;
