<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">


        <!-- Tailwind CSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <script>
            tailwind.config = {
                theme: {
                    extend: {
                        colors: {
                            primary: '#1e7b8e',
                        }
                    }
                }
            }
        </script>

        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <style>
            .line-clamp-2 {
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow: hidden;
            }

            .prose {
                max-width: 65ch;
                color: #374151;
            }

            .prose h3 {
                font-size: 1.25rem;
                margin-top: 1.5rem;
                margin-bottom: 0.5rem;
                font-weight: 600;
            }

            .prose h4 {
                font-size: 1rem;
                margin-top: 1.5rem;
                margin-bottom: 0.5rem;
                font-weight: 600;
            }

            .prose p {
                margin-top: 1rem;
                margin-bottom: 1rem;
            }

            .prose ul, .prose ol {
                margin-top: 1rem;
                margin-bottom: 1rem;
                padding-left: 1.5rem;
            }

            .prose li {
                margin-top: 0.25rem;
                margin-bottom: 0.25rem;
            }

            .prose ul > li {
                list-style-type: disc;
            }

            .prose ol > li {
                list-style-type: decimal;
            }
        </style>
    </head>
    <body class="min-h-screen bg-gray-50">
        <!-- Header -->
        <jsp:include page="/customer/headerCustomer.jsp" />

        <!-- Navigation -->
        <nav class="bg-white shadow">
            <div class="container mx-auto px-4">

            </div>
        </nav>

        <!-- Breadcrumb -->
        <div class="container mx-auto px-4 py-3">
            <div class="flex items-center text-sm text-gray-500">
                <a href="/homepage" class="hover:text-primary">Trang chủ</a>
                <i class="fas fa-chevron-right h-4 w-4 mx-1"></i>
            </div>
        </div>


        <!-- Product Detail -->
        <main class="container mx-auto px-4 py-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                <!-- Product Images -->
                <div class="bg-white rounded-lg p-4 shadow-sm"> <div class="relative aspect-square overflow-hidden rounded-lg mb-4"> <img src="<c:url value='${service.imageURL}' />" alt="img" class="object-contain w-full h-full" id="mainProductImage" /> </div>
                    <div class="flex gap-2 overflow-x-auto pb-2">
                        <c:forEach var="img" items="${imgList}">
                            <button 
                                onclick="changeImage('${img.imageURL}', this)"
                                class="relative border rounded min-w-[60px] h-[60px] border-gray-200"
                                >
                                <img 
                                    src="<c:url value='${img.imageURL}' />" 
                                    alt="Thumbnail"
                                    class="object-contain w-full h-full"
                                    />
                            </button>
                        </c:forEach>
                    </div>  

                </div>

                <!-- Product Info -->
                <div class="space-y-6">
                    <div>
                        <h1 class="text-2xl font-bold text-gray-800 mb-2">${service.serviceName}</h1>
                        <div class="flex items-center gap-4 text-sm">
                            <div class="flex items-center">
                                <span class="text-yellow-500">★★★★★</span>
                                <span class="text-gray-500 ml-1">${totalRated}</span>
                            </div>
                            <a href="#reviews" class="text-primary hover:underline">
                                Xem ${count} đánh giá
                            </a>
                            <span class="text-gray-500">Đã bán ${total}</span>
                        </div>
                    </div>

                    <div class="bg-gray-50 p-4 rounded-lg">
                        <div class="flex items-baseline gap-2">
                            <span class="text-3xl font-bold text-red-600">
                                <fmt:formatNumber value="${service.servicePrice}" pattern="#,###" />.000 VNĐ
                            </span>
                            <span class="text-lg text-gray-500 line-through">
                                <fmt:formatNumber value="${service.servicePrice}" pattern="#,###" />.000 VNĐ
                            </span>

                            <span class="bg-red-600 text-white text-xs px-2 py-0.5 rounded">-21%</span>
                        </div>


                    </div>



                    <div class="flex gap-4">

                        <div class="buttons">
                            <!-- Add to Cart Form --> 
                            <c:choose>
                                <c:when test="${sessionScope.roleID == '4'}">
                                    <form action="AddCart" method="POST" onsubmit="return showNotification();">
                                        <input type="hidden" name="serviceID" value="${service.serviceID}">
                                        <button class="btn btn-success" type="submit">Thêm vào giỏ hàng</button>
                                    </form>  
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-success"><a href="${pageContext.request.contextPath}/login" style="text-decoration: none;color: white">Thêm vào giỏ hàng</a></button><br>
                                </c:otherwise>
                            </c:choose>

                            <!-- Feedback Button -->

                        </div>
                    </div>


                </div>
            </div>

            <!-- Product Tabs -->
            <div class="mt-8">
                <div class="border-b">
                    <div class="flex">
                        <button class="px-4 py-2 font-medium border-b-2 border-transparent" id="tab-description" onclick="openTab('description')">
                            Mô tả sản phẩm
                        </button>
                        <button class="px-4 py-2 font-medium border-b-2 border-transparent" id="tab-reviews" onclick="openTab('reviews')">
                            Đánh giá (${count})
                        </button>
                    </div>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-sm mt-4">
                    <!-- Description Tab -->
                    <div id="content-description" class="tab-content hidden">
                        <div class="prose max-w-none">
                            <h3>${service.serviceDetail}</h3>

                        </div>
                    </div>

                    <!-- Reviews Tab -->
                    <div id="content-reviews" class="tab-content hidden">
                        <div class="space-y-6">
                            <div class="flex items-center gap-4">
                                <div class="text-center">
                                    <div class="text-5xl font-bold text-yellow-500">${totalRated}</div>
                                    <div class="text-yellow-500 text-xl">★★★★★</div>
                                    <div class="text-sm text-gray-500">${count} đánh giá</div>
                                </div>

                            </div>

                            <div class="space-y-4">



                                <c:forEach var="feedBack" items="${feedBack}">
                                    <div class="p-4 border rounded-lg">
                                        <div class="flex justify-between mb-2">
                                            <div class="flex items-center gap-2">
                                                <div class="w-10 h-10 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 font-medium">
                                                    ${feedBack.user.name.substring(0, 1)}
                                                </div>
                                                <div>
                                                    <div class="font-medium">${feedBack.user.name}</div>
                                                    <div class="text-yellow-500 text-sm">
                                                        <c:forEach begin="1" end="${feedBack.rated}">★</c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="text-sm text-gray-500">${feedBack.creationDate}</div>
                                        </div>
                                        <p class="text-sm text-gray-700">${feedBack.feedbackDetail}</p>
                                    </div>
                                </c:forEach>

                                <!-- Form nhập bình luận -->
                                <div class="mt-4 p-4 border rounded-lg">
                                    <form action="viewDetailProduct" method="post">
                                        <input type="hidden" name="id" value="${service.serviceID}" />

                                        <label for="rated" class="block text-sm font-medium text-gray-700">Đánh giá:</label>
                                        <select name="rated" id="rated" class="border p-2 rounded-md w-full">
                                            <option value="1">★</option>
                                            <option value="2">★★</option>
                                            <option value="3">★★★</option>
                                            <option value="4">★★★★</option>
                                            <option value="5">★★★★★</option>
                                        </select>

                                        <label for="feedbackDetail" class="block text-sm font-medium text-gray-700 mt-2">Bình luận:</label>
                                        <textarea name="feedbackDetail" id="feedbackDetail" class="border p-2 rounded-md w-full" rows="3" placeholder="Nhập bình luận..."></textarea>

                                        <button type="submit" class="mt-3 bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
                                            Gửi đánh giá
                                        </button>
                                    </form>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
        </main>

        <!-- Related Products -->
        <section class="container mx-auto px-4 py-8">
            <h2 class="text-xl font-bold mb-6">Sản phẩm tương tự</h2>
            <div class="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-4">
                <c:forEach var="i" begin="1" end="5">
                    <div class="bg-white rounded-lg shadow-sm overflow-hidden">
                        <div class="relative aspect-square">
                            <img 
                                src="<c:url value='/images/product/related-${i}.jpg' />" 
                                alt="Sản phẩm ${i}"
                                class="object-contain w-full h-full"
                                />
                            <span class="absolute top-2 left-2 bg-red-600 text-white text-xs px-2 py-0.5 rounded">-15%</span>
                        </div>
                        <div class="p-3">
                            <h3 class="font-medium text-sm line-clamp-2 mb-2">Máy đo huyết áp Omron HEM-7120</h3>
                            <div class="text-red-600 font-bold">950.000₫</div>
                            <div class="text-gray-500 text-xs line-through">1.120.000₫</div>
                            <div class="flex items-center text-xs mt-2">
                                <span class="text-yellow-500">★★★★★</span>
                                <span class="text-gray-500 ml-1">(86)</span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- Footer -->
        <footer class="bg-primary text-white mt-8">
            <div class="container mx-auto px-4 py-8">
                <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
                    <div>
                        <h3 class="font-bold text-lg mb-4">Về HealthCare</h3>
                        <ul class="space-y-2 text-sm">
                            <li><a href="#" class="hover:underline">Giới thiệu</a></li>
                            <li><a href="#" class="hover:underline">Tuyển dụng</a></li>
                            <li><a href="#" class="hover:underline">Chính sách bảo mật</a></li>
                            <li><a href="#" class="hover:underline">Điều khoản sử dụng</a></li>
                            <li><a href="#" class="hover:underline">Liên hệ</a></li>
                        </ul>
                    </div>
                    <div>
                        <h3 class="font-bold text-lg mb-4">Hỗ trợ khách hàng</h3>
                        <ul class="space-y-2 text-sm">
                            <li><a href="#" class="hover:underline">Trung tâm trợ giúp</a></li>
                            <li><a href="#" class="hover:underline">Hướng dẫn mua hàng</a></li>
                            <li><a href="#" class="hover:underline">Phương thức thanh toán</a></li>
                            <li><a href="#" class="hover:underline">Chính sách đổi trả</a></li>
                            <li><a href="#" class="hover:underline">Chính sách vận chuyển</a></li>
                        </ul>
                    </div>
                    <div>
                        <h3 class="font-bold text-lg mb-4">Dịch vụ y tế</h3>
                        <ul class="space-y-2 text-sm">
                            <li><a href="#" class="hover:underline">Đặt lịch khám</a></li>
                            <li><a href="#" class="hover:underline">Tư vấn sức khỏe từ xa</a></li>
                            <li><a href="#" class="hover:underline">Xét nghiệm tại nhà</a></li>
                            <li><a href="#" class="hover:underline">Giao thuốc tận nơi</a></li>
                            <li><a href="#" class="hover:underline">Bác sĩ gia đình</a></li>
                        </ul>
                    </div>
                    <div>
                        <h3 class="font-bold text-lg mb-4">Kết nối với chúng tôi</h3>
                        <div class="flex gap-4 mb-4">
                            <a href="#" class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center hover:bg-white/30">
                                <i class="fab fa-facebook-f"></i>
                            </a>
                            <a href="#" class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center hover:bg-white/30">
                                <i class="fab fa-instagram"></i>
                            </a>
                            <a href="#" class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center hover:bg-white/30">
                                <i class="fab fa-twitter"></i>
                            </a>
                            <a href="#" class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center hover:bg-white/30">
                                <i class="fab fa-linkedin-in"></i>
                            </a>
                        </div>
                        <h3 class="font-bold text-lg mb-4">Tải ứng dụng</h3>
                        <div class="flex gap-2">
                            <a href="#" class="bg-black rounded-lg p-2 flex items-center gap-2">
                                <i class="fab fa-apple"></i>
                                <div class="text-xs">
                                    <div>Tải về trên</div>
                                    <div class="font-medium">App Store</div>
                                </div>
                            </a>
                            <a href="#" class="bg-black rounded-lg p-2 flex items-center gap-2">
                                <i class="fab fa-google-play"></i>
                                <div class="text-xs">
                                    <div>Tải về trên</div>
                                    <div class="font-medium">Google Play</div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="border-t border-white/20 mt-8 pt-6 text-center text-sm">
                    <p>© 2023 HealthCare. Tất cả các quyền được bảo lưu.</p>
                </div>
            </div>
        </footer>

        <!-- JavaScript -->
        <script>
            // Countdown timer
            function updateCountdown() {
                const now = new Date();
                const end = new Date();
                end.setHours(23, 59, 59);

                const diff = end - now;

                const hours = Math.floor(diff / (1000 * 60 * 60));
                const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((diff % (1000 * 60)) / 1000);

                document.getElementById('hours').textContent = hours.toString().padStart(2, '0');
                document.getElementById('minutes').textContent = minutes.toString().padStart(2, '0');
                document.getElementById('seconds').textContent = seconds.toString().padStart(2, '0');
            }

            setInterval(updateCountdown, 1000);
            updateCountdown();

            // Tab switching
            function openTab(tabName) {
                // Hide all tab contents
                const tabContents = document.querySelectorAll('.tab-content');
                tabContents.forEach(content => {
                    content.classList.add('hidden');
                });

                // Remove active class from all tabs
                const tabs = document.querySelectorAll('[id^="tab-"]');
                tabs.forEach(tab => {
                    tab.classList.remove('border-primary', 'text-primary');
                    tab.classList.add('border-transparent');
                });

                // Show the selected tab content
                document.getElementById('content-' + tabName).classList.remove('hidden');

                // Add active class to the selected tab
                document.getElementById('tab-' + tabName).classList.add('border-primary', 'text-primary');
                document.getElementById('tab-' + tabName).classList.remove('border-transparent');
            }

            // Image gallery
            function changeImage(index) {
                const mainImage = document.getElementById('mainProductImage');
                mainImage.src = '<c:url value="/images/product/blood-pressure-monitor-' + index + '.jpg" />';

                // Update thumbnail border
                const thumbnails = document.querySelectorAll('.min-w-\\[60px\\]');
                thumbnails.forEach((thumb, i) => {
                    if (i + 1 === index) {
                        thumb.classList.remove('border-gray-200');
                        thumb.classList.add('border-primary');
                    } else {
                        thumb.classList.remove('border-primary');
                        thumb.classList.add('border-gray-200');
                    }
                });
            }

            // Initialize the page
            document.addEventListener('DOMContentLoaded', function () {
                // Set the first tab as active by default
                openTab('specs');
            });
        </script>


        <script>
            function changeImage(imageUrl, button) {
                // Thay đổi ảnh chính 
                document.getElementById('mainProductImage').src = imageUrl;
                // Loại bỏ viền cũ của tất cả button 
                document.querySelectorAll('.flex button').forEach(btn => {
                    btn.classList.remove('border-primary');
                    btn.classList.add('border-gray-200');
                });
                // Đổi viền của ảnh được chọn 
                button.classList.remove('border-gray-200');
                button.classList.add('border-primary');
            }
        </script>
    </body>
</html>

