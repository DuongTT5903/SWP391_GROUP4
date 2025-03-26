<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>HealthCare - Máy đo huyết áp Omron HEM-8712</title>

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
        <header class="bg-primary text-white">
            <div class="container mx-auto px-4 py-3">
                <div class="flex items-center justify-between">
                    <a href="/" class="flex items-center gap-2">
                        <div class="relative w-10 h-10">
                            <div class="absolute inset-0 bg-white rounded-full flex items-center justify-center">
                                <span class="text-primary text-xl font-bold">HC</span>
                            </div>
                        </div>
                        <span class="text-xl font-bold">HealthCare</span>
                    </a>

                    <div class="flex-1 max-w-xl mx-4">
                        <div class="relative">
                            <input 
                                type="text" 
                                placeholder="Tìm sản phẩm, dịch vụ y tế..." 
                                class="w-full pl-4 pr-10 py-2 rounded-full border-0 bg-white/90 text-gray-800"
                                />
                            <i class="fas fa-search absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500"></i>
                        </div>
                    </div>

                    <div class="flex items-center gap-4">
                        <a href="/account" class="flex items-center gap-1 text-sm">
                            <i class="fas fa-user h-5 w-5"></i>
                            <span>Đăng nhập</span>
                        </a>

                        <a href="/cart" class="flex items-center gap-1 text-sm">
                            <div class="relative">
                                <i class="fas fa-shopping-cart h-5 w-5"></i>
                                <span class="absolute -top-2 -right-2 h-5 w-5 flex items-center justify-center p-0 bg-red-500 text-white text-xs rounded-full">2</span>
                            </div>
                            <span>Giỏ hàng</span>
                        </a>

                        <div class="flex items-center gap-1 text-sm">
                            <i class="fas fa-map-marker-alt h-5 w-5"></i>
                            <span>Hà Nội</span>
                            <i class="fas fa-chevron-down h-4 w-4"></i>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Navigation -->
        <nav class="bg-white shadow">
            <div class="container mx-auto px-4">
                <div class="flex items-center gap-6 overflow-x-auto py-2 text-sm">
                    <a href="/thuoc" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Thuốc</span>
                    </a>
                    <a href="/thiet-bi" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Thiết bị y tế</span>
                    </a>
                    <a href="/vitamin" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Vitamin & TPCN</span>
                    </a>
                    <a href="/cham-soc" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Chăm sóc cá nhân</span>
                    </a>
                    <a href="/me-be" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Mẹ & Bé</span>
                    </a>
                    <a href="/dat-lich" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Đặt lịch khám</span>
                    </a>
                    <a href="/tu-van" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Tư vấn sức khỏe</span>
                    </a>
                    <a href="/bai-viet" class="flex items-center gap-1 whitespace-nowrap px-2 py-1 hover:text-primary">
                        <span class="font-medium">Bài viết y khoa</span>
                    </a>
                </div>
            </div>
        </nav>

        <!-- Breadcrumb -->
        <div class="container mx-auto px-4 py-3">
            <div class="flex items-center text-sm text-gray-500">
                <a href="/" class="hover:text-primary">Trang chủ</a>
                <i class="fas fa-chevron-right h-4 w-4 mx-1"></i>
                <a href="/thiet-bi" class="hover:text-primary">Thiết bị y tế</a>
                <i class="fas fa-chevron-right h-4 w-4 mx-1"></i>
                <span class="text-gray-700">Máy đo huyết áp Omron HEM-8712</span>
            </div>
        </div>


        <!-- Product Detail -->
        <main class="container mx-auto px-4 py-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                <!-- Product Images -->
                <div class="bg-white rounded-lg p-4 shadow-sm">
                    <div class="relative aspect-square overflow-hidden rounded-lg mb-4">
                        <img 
                            src="<c:url value='${service.imageURL}' />" 
                            alt="img" 
                            class="object-contain w-full h-full"
                            id="mainProductImage"
                            />
                    </div>

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
                        <h1 class="text-2xl font-bold text-gray-800 mb-2">Máy đo huyết áp Omron HEM-8712</h1>
                        <div class="flex items-center gap-4 text-sm">
                            <div class="flex items-center">
                                <span class="text-yellow-500">★★★★★</span>
                                <span class="text-gray-500 ml-1">4.9</span>
                            </div>
                            <a href="#reviews" class="text-primary hover:underline">
                                Xem 128 đánh giá
                            </a>
                            <span class="text-gray-500">Đã bán 5.2k</span>
                        </div>
                    </div>

                    <div class="bg-gray-50 p-4 rounded-lg">
                        <div class="flex items-baseline gap-2">
                            <span class="text-3xl font-bold text-red-600">1.250.000₫</span>
                            <span class="text-lg text-gray-500 line-through">1.590.000₫</span>
                            <span class="bg-red-600 text-white text-xs px-2 py-0.5 rounded">-21%</span>
                        </div>

                        <div class="mt-4 bg-white p-3 rounded-lg border border-dashed border-primary">
                            <div class="flex items-center gap-2 text-primary font-medium">
                                <span class="bg-primary text-white text-xs px-2 py-0.5 rounded">Online giá tốt</span>
                                Kết thúc sau: 
                                <span class="bg-gray-100 px-2 py-1 rounded text-gray-800" id="hours">23</span>:
                                <span class="bg-gray-100 px-2 py-1 rounded text-gray-800" id="minutes">12</span>:
                                <span class="bg-gray-100 px-2 py-1 rounded text-gray-800" id="seconds">45</span>
                            </div>
                        </div>
                    </div>

                    <div class="space-y-4">
                        <h3 class="font-medium text-gray-800">Khuyến mãi</h3>
                        <ul class="space-y-2 text-sm">
                            <li class="flex items-start gap-2">
                                <span class="text-primary font-bold">•</span>
                                <span>Miễn phí vận chuyển toàn quốc cho đơn hàng từ 500.000₫</span>
                            </li>
                            <li class="flex items-start gap-2">
                                <span class="text-primary font-bold">•</span>
                                <span>Tặng phiếu khám sức khỏe trị giá 200.000₫ tại hệ thống bệnh viện đối tác</span>
                            </li>
                            <li class="flex items-start gap-2">
                                <span class="text-primary font-bold">•</span>
                                <span>Trả góp 0% lãi suất qua thẻ tín dụng</span>
                            </li>
                            <li class="flex items-start gap-2">
                                <span class="text-primary font-bold">•</span>
                                <span>Bảo hành chính hãng 24 tháng, đổi mới trong 30 ngày nếu lỗi nhà sản xuất</span>
                            </li>
                        </ul>
                    </div>

                    <div class="flex gap-4">
                        <button class="flex-1 h-12 border border-gray-300 rounded-md flex items-center justify-center gap-2 hover:bg-gray-50">
                            <i class="fas fa-heart mr-2"></i>
                            Thêm vào yêu thích
                        </button>
                        <button class="flex-1 h-12 bg-primary text-white rounded-md hover:bg-primary/90">
                            Mua ngay
                        </button>
                    </div>

                    <div class="grid grid-cols-2 gap-4 text-sm">
                        <div class="flex items-center gap-2 text-gray-600">
                            <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                                <i class="fas fa-heart text-primary"></i>
                            </div>
                            <span>Cam kết hàng chính hãng 100%</span>
                        </div>
                        <div class="flex items-center gap-2 text-gray-600">
                            <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                                <i class="fas fa-truck text-primary"></i>
                            </div>
                            <span>Giao hàng toàn quốc</span>
                        </div>
                        <div class="flex items-center gap-2 text-gray-600">
                            <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                                <i class="fas fa-headset text-primary"></i>
                            </div>
                            <span>Tư vấn sử dụng 24/7</span>
                        </div>
                        <div class="flex items-center gap-2 text-gray-600">
                            <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                                <i class="fas fa-shield-alt text-primary"></i>
                            </div>
                            <span>Bảo hành chính hãng 24 tháng</span>
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
                                    <div class="text-5xl font-bold text-yellow-500">4.9</div>
                                    <div class="text-yellow-500 text-xl">★★★★★</div>
                                    <div class="text-sm text-gray-500">${count} đánh giá</div>
                                </div>
                                <div class="flex-1 space-y-1">
                                    <div class="flex items-center gap-2">
                                        <div class="text-sm w-8">5 ★</div>
                                        <div class="flex-1 h-2 bg-gray-200 rounded-full overflow-hidden">
                                            <div class="bg-yellow-500 h-full rounded-full" style="width: 90%"></div>
                                        </div>
                                        <div class="text-sm text-gray-500">90%</div>
                                    </div>
                                    <div class="flex items-center gap-2">
                                        <div class="text-sm w-8">4 ★</div>
                                        <div class="flex-1 h-2 bg-gray-200 rounded-full overflow-hidden">
                                            <div class="bg-yellow-500 h-full rounded-full" style="width: 7%"></div>
                                        </div>
                                        <div class="text-sm text-gray-500">7%</div>
                                    </div>
                                    <div class="flex items-center gap-2">
                                        <div class="text-sm w-8">3 ★</div>
                                        <div class="flex-1 h-2 bg-gray-200 rounded-full overflow-hidden">
                                            <div class="bg-yellow-500 h-full rounded-full" style="width: 2%"></div>
                                        </div>
                                        <div class="text-sm text-gray-500">2%</div>
                                    </div>
                                    <div class="flex items-center gap-2">
                                        <div class="text-sm w-8">2 ★</div>
                                        <div class="flex-1 h-2 bg-gray-200 rounded-full overflow-hidden">
                                            <div class="bg-yellow-500 h-full rounded-full" style="width: 1%"></div>
                                        </div>
                                        <div class="text-sm text-gray-500">1%</div>
                                    </div>
                                    <div class="flex items-center gap-2">
                                        <div class="text-sm w-8">1 ★</div>
                                        <div class="flex-1 h-2 bg-gray-200 rounded-full overflow-hidden">
                                            <div class="bg-yellow-500 h-full rounded-full" style="width: 0%"></div>
                                        </div>
                                        <div class="text-sm text-gray-500">0%</div>
                                    </div>
                                </div>
                            </div>

                            <div class="space-y-4">



                                <c:forEach var="feedBack" items="${feedBack}">
                                    <div class="p-4 border rounded-lg">
                                        <div class="flex justify-between mb-2">
                                            <div class="flex items-center gap-2">
                                                <div class="w-10 h-10 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 font-medium">NT</div>
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

                                <!-- Hardcoded reviews for demonstration -->

                                <div class="p-4 border rounded-lg">
                                    <div class="flex justify-between mb-2">
                                        <div class="flex items-center gap-2">
                                            <div class="w-10 h-10 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 font-medium">NT</div>
                                            <div>
                                                <div class="font-medium">Nguyễn Thành</div>
                                                <div class="text-yellow-500 text-sm">★★★★★</div>
                                            </div>
                                        </div>
                                        <div class="text-sm text-gray-500">12/03/2023</div>
                                    </div>
                                    <p class="text-sm text-gray-700">
                                        Máy đo chính xác, dễ sử dụng. Tôi đã so sánh với kết quả đo tại phòng khám và thấy sai số rất nhỏ. Màn hình lớn, dễ đọc số. Pin dùng được lâu. Rất hài lòng với sản phẩm này.
                                    </p>
                                </div>

                            </div>

                            <button class="w-full py-2 px-4 border border-gray-300 rounded-md hover:bg-gray-50">Xem thêm đánh giá</button>
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




        <!-- JavaScript để đổi ảnh chính -->
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

