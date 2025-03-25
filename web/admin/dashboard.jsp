<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<%
    // Lấy dữ liệu doanh thu từ request
    Map<String, Double> revenueByCategory = (Map<String, Double>) request.getAttribute("revenueByCategory");

    // Kiểm tra nếu null, gán giá trị mặc định để tránh lỗi
    if (revenueByCategory == null) {
        revenueByCategory = new HashMap<>();
    }

    // Tính tổng doanh thu và chuẩn bị dữ liệu biểu đồ
    double totalRevenue = 0;
    String categoryNames = "", revenues = "";
    for (Map.Entry<String, Double> entry : revenueByCategory.entrySet()) {
        categoryNames += "\"" + entry.getKey() + "\",";
        revenues += entry.getValue() + ",";
        totalRevenue += entry.getValue();
    }

    // Lấy dữ liệu khác từ request
    Map<Integer, Integer> statusCounts = (Map<Integer, Integer>) request.getAttribute("statusCounts");
    int success = (statusCounts != null && statusCounts.containsKey(1)) ? statusCounts.get(1) : 0;
    int cancelled = (statusCounts != null && statusCounts.containsKey(0)) ? statusCounts.get(0) : 0;

    Map<String, Double> serviceRatings = (Map<String, Double>) request.getAttribute("serviceRatings");
    String serviceNames = "", ratings = "", colors = "";
    if (serviceRatings != null) {
        for (Map.Entry<String, Double> entry : serviceRatings.entrySet()) {
            serviceNames += "\"" + entry.getKey() + "\",";
            ratings += entry.getValue() + ",";
            colors += "'#4CAF50',"; 
        }
    }

    Map<String, Integer> reservationTrends = (Map<String, Integer>) request.getAttribute("reservationTrends");
    String dates = "", counts = "";
    if (reservationTrends != null) {
        for (Map.Entry<String, Integer> entry : reservationTrends.entrySet()) {
            dates += "\"" + entry.getKey() + "\",";
            counts += entry.getValue() + ",";
        }
    }

    int newReservations = (request.getAttribute("newReservations") != null) ? (Integer) request.getAttribute("newReservations") : 0;
%>

    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f0f2f5;
            text-align: center;
        }
        h2 { color: #333; }
        .dashboard-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 20px;
            max-width: 1200px;
            margin: auto;
        }
        .chart-container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        canvas {
            width: 100% !important;
            height: 300px !important;
        }
    </style>
     <jsp:include page="./AdminHeader.jsp" />
</head>
<body>
    
    <h2>Thống kê Đặt chỗ, Đánh giá & Xu hướng</h2>

    <div class="dashboard-container">
        <div class="chart-container">
            <h3>Trạng thái đặt chỗ</h3>
            <canvas id="pieChart"></canvas>
        </div>

        <div class="chart-container">
            <h3>Đánh giá dịch vụ</h3>
            <canvas id="barChart"></canvas>
        </div>

        <div class="chart-container">
            <h3>Xu hướng đặt chỗ (7 ngày qua)</h3>
            <canvas id="lineChart"></canvas>
        </div>

        <div class="chart-container">
            <h3>Khách hàng mới đặt chỗ</h3>
            <canvas id="newReservationsChart"></canvas>
        </div>

        <div class="chart-container">
            <h3>Doanh thu theo danh mục</h3>
            <canvas id="revenueChart"></canvas>
        </div>
    </div>

    <script>
        // Biểu đồ tròn - Trạng thái đặt chỗ
        new Chart(document.getElementById('pieChart'), {
            type: 'pie',
            data: {
                labels: ["Thành công", "Bị hủy"],
                datasets: [{
                    data: [<%= success %>, <%= cancelled %>],
                    backgroundColor: ['#36A2EB', '#FF6384']
                }]
            }
        });

      new Chart(document.getElementById('barChart'), {
    type: 'bar',
    data: {
        labels: [<%= serviceNames %>],
        datasets: [{
            label: 'Điểm đánh giá',
            data: [<%= ratings %>],
            backgroundColor: [<%= colors %>],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: { 
                beginAtZero: true, 
                max: 5 
            }
        },
        plugins: {
            tooltip: {
                enabled: true, // Bật hiển thị tooltip
                mode: 'index', // Hiển thị tất cả dữ liệu tại vị trí trỏ vào
                intersect: false, // Tooltip xuất hiện dù không trỏ đúng vào cột
                animation: {
                    duration: 200, // Làm mượt hiệu ứng tooltip
                    easing: 'easeOutExpo' 
                },
                position: 'nearest', // Giúp tooltip hiển thị gần con trỏ hơn
                callbacks: {
                    label: function(tooltipItem) {
                        return 'Điểm: ' + tooltipItem.raw.toFixed(1); // Hiển thị giá trị có 1 số lẻ
                    }
                }
            }
        },
        hover: {
            mode: 'nearest', 
            intersect: false, // Tooltip dễ xuất hiện hơn khi di chuột
            animationDuration: 100 // Giúp tooltip phản hồi nhanh hơn
        },
        responsive: true,
        maintainAspectRatio: false,
        interaction: {
            mode: 'index',
            intersect: false
        },
        layout: {
            padding: 10 // Tăng padding để tránh tooltip bị cắt
        },
        elements: {
            bar: {
                hoverBackgroundColor: 'rgba(0, 123, 255, 0.5)', // Hiệu ứng khi trỏ vào
                hoverBorderWidth: 2 // Giúp thanh nổi bật khi hover
            }
        },
        clip: false // Ngăn tooltip bị cắt ở cột cuối
    }
});
// Biểu đồ đường - Xu hướng đặt chỗ (7 ngày qua)
new Chart(document.getElementById('lineChart'), {
    type: 'line',
    data: {
        labels: [<%= dates %>],
        datasets: [{
            label: 'Đặt chỗ thành công',
            data: [<%= counts %>],
            borderColor: '#007BFF',
            backgroundColor: 'rgba(0, 123, 255, 0.2)',
            pointBackgroundColor: '#007BFF', // Màu của điểm
            pointBorderColor: '#fff', // Viền điểm
            pointRadius: 5, // Kích thước điểm
            pointHoverRadius: 7, // Kích thước khi trỏ vào
            tension: 0.4, // Làm đường cong mượt hơn
            fill: true
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    stepSize: 1, // Chỉ hiển thị số nguyên
                    callback: function(value) { return Math.floor(value); } // Không hiển thị số lẻ
                }
            }
        },
        plugins: {
            tooltip: {
                enabled: true, 
                mode: 'index',   // Hiển thị tất cả dataset tại vị trí trỏ vào
                intersect: false, // Tooltip xuất hiện dù không trỏ đúng vào điểm
                animation: {
                    duration: 150, // Giảm delay khi tooltip xuất hiện
                    easing: 'easeOutExpo' // Hiệu ứng mượt hơn
                },
                position: 'nearest', // Giúp tooltip gần con trỏ hơn
                callbacks: {
                    label: function(tooltipItem) {
                        return 'Số lượt đặt chỗ: ' + tooltipItem.raw; // Hiển thị số lượt đặt chỗ
                    }
                }
            },
            legend: {
                display: true, // Hiển thị chú thích
                position: 'top'
            }
        },
        hover: {
            mode: 'nearest', 
            intersect: false, // Mượt hơn khi di chuyển chuột
            animationDuration: 100 // Giúp tooltip phản hồi nhanh hơn
        },
        elements: {
            line: {
                borderWidth: 2 // Làm đường rõ hơn
            },
            point: {
                hoverBackgroundColor: '#FF5733', // Hiệu ứng khi trỏ vào điểm
                hoverBorderWidth: 2
            }
        },
        layout: {
            padding: 10 // Tránh tooltip bị cắt
        },
        clip: false // Ngăn tooltip bị cắt ở điểm cuối
    }
});




        // Biểu đồ khách hàng mới đặt chỗ
        new Chart(document.getElementById('newReservationsChart'), {
            type: 'bar',
            data: {
                labels: ["Khách hàng mới"],
                datasets: [{
                    label: 'Số lượng',
                    data: [<%= newReservations %>],
                    backgroundColor: '#FF9800'
                }]
            },
            options: { scales: { y: { beginAtZero: true } } }
        });

        // Biểu đồ doughnut - Doanh thu theo danh mục
        new Chart(document.getElementById('revenueChart'), {
            type: 'doughnut',
            data: {
                labels: [<%= categoryNames %>],
                datasets: [{
                    label: 'Doanh thu',
                    data: [<%= revenues %>],
                    backgroundColor: ['#FF5733', '#36A2EB', '#4CAF50', '#FFC107', '#8E44AD']
                }]
            },
            options: {
                plugins: { 
                    legend: { display: true },
                    title: {
                        display: true,
                        text: 'Tổng doanh thu: <%= String.format("%,.0f", totalRevenue) %> VND',
                        font: { size: 16 }
                    }
                }
            }
        });
    </script>
</body>
</html>
