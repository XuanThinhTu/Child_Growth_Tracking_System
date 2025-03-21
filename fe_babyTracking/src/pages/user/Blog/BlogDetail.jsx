import React from 'react';
import { useParams, Link } from 'react-router-dom';

// Sử dụng cùng mock data như trang Blog (bạn có thể tách riêng hoặc lấy từ API)
const mockBlogs = [
    {
        id: 1,
        title: 'Bài viết đầu tiên',
        content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Phasellus imperdiet nulla et dictum interdum. Quisque dignissim eros sit amet ligula consectetur, vitae placerat metus facilisis.',
        createdAt: '2025-03-16T17:17:19.212Z',
        authorName: 'Tác giả 1',
        blogImages: [
            { id: 1, url: 'https://via.placeholder.com/1200x600', publicId: 'image1' }
        ],
        category: 'Category A',
    },
    {
        id: 2,
        title: 'Bài viết thứ hai',
        content: 'Quisque vel urna a arcu lacinia vestibulum. Nulla facilisi. Fusce posuere, tortor sed cursus feugiat, nunc augue blandit nunc, id blandit felis ligula ut est. Curabitur ac libero nec erat fringilla tincidunt sed ac lorem. Donec at dapibus magna, ac porttitor velit.',
        createdAt: '2025-03-15T12:00:00.000Z',
        authorName: 'Tác giả 2',
        blogImages: [
            { id: 2, url: 'https://via.placeholder.com/1200x600', publicId: 'image2' }
        ],
        category: 'Category B',
    },
    {
        id: 3,
        title: 'Bài viết thứ ba',
        content: 'Suspendisse potenti. Proin ut dui sed metus pharetra hendrerit vel non mi. Nulla ornare faucibus ex, non facilisis nisl. Aliquam erat volutpat. Mauris varius nunc sed nulla tincidunt, non bibendum urna tincidunt.',
        createdAt: '2025-03-14T08:30:00.000Z',
        authorName: 'Tác giả 3',
        blogImages: [
            { id: 3, url: 'https://via.placeholder.com/1200x600', publicId: 'image3' }
        ],
        category: 'Category A',
    },
];

const BlogDetail = () => {
    const { id } = useParams();
    const post = mockBlogs.find(p => p.id === parseInt(id));

    if (!post) {
        return (
            <div className="container mx-auto p-8">
                <h1 className="text-3xl font-bold mb-4">Bài viết không tồn tại</h1>
                <Link to="/blog" className="text-green-800 hover:underline">&larr; Quay lại blog</Link>
            </div>
        );
    }

    return (
        <div className="container mx-auto p-8">
            <Link to="/blog" className="text-green-800 hover:underline mb-4 inline-block">&larr; Quay lại blog</Link>
            <div className="bg-white rounded shadow overflow-hidden">
                {post.blogImages && post.blogImages.length > 0 && (
                    <img
                        src={post.blogImages[0].url}
                        alt={post.title}
                        className="w-full h-96 object-cover"
                    />
                )}
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
