/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Ensure Chart.js runs after the page loads
document.addEventListener("DOMContentLoaded", function () {
    genViewChart();
});

function selectCard(card) {
    if (card.classList.contains('selected')) {
        return; // Do nothing if already selected
    }

    // Remove 'selected' class from all cards
    document.querySelectorAll('.cardcard').forEach(el => el.classList.remove('selected'));
    // Add 'selected' class to the clicked card
    card.classList.add('selected');
    if (card.classList.contains('viewChart')) {
        genViewChart();
    }
    if (card.classList.contains('loginChart')) {
        genLoginChart();
    }
    if (card.classList.contains('saleChart')) {
        genSaleChart();
    }
    if (card.classList.contains('ticketChart')) {
        genTicketChart();
    }
}

let visitorChartInstance = null;

function genViewChart() {
    fetch('genViewChart') // Fetch data from the Servlet
            .then(response => response.json()) // Convert response to JSON
            .then(data => {
                // Unique Visitor Line Chart
                const visitorCtx = document.getElementById('visitorChart');
                if (visitorCtx) {
                    if (visitorChartInstance !== null) {
                        visitorChartInstance.destroy();
                    }
                    visitorChartInstance = new Chart(visitorCtx, {
                        type: 'bar',
                        data: {
                            labels: data.labels,
                            datasets: [{
                                    label: 'Total Views',
                                    data: data.data,
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 2,
                                    backgroundColor: 'rgba(54, 162, 235, 1)',
                                }]
                        },
                        options: {responsive: true}
                    });
                }
            })
}

function genLoginChart() {
    fetch('genLoginChart') // Fetch data from the Servlet
            .then(response => response.json()) // Convert response to JSON
            .then(data => {
                // Unique Visitor Line Chart
                const visitorCtx = document.getElementById('visitorChart');
                if (visitorCtx) {
                    if (visitorChartInstance !== null) {
                        visitorChartInstance.destroy();
                    }
                    visitorChartInstance = new Chart(visitorCtx, {
                        type: 'bar',
                        data: {
                            labels: data.labels,
                            datasets: [{
                                    label: 'Total Login',
                                    data: data.data,
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 2,
                                    backgroundColor: 'rgba(54, 162, 235, 1)',
                                }]
                        },
                        options: {responsive: true}
                    });
                }
            })
}

function genSaleChart() {
    fetch('genSaleChart') // Fetch data from the Servlet
            .then(response => response.json()) // Convert response to JSON
            .then(data => {
                // Unique Visitor Line Chart
                const visitorCtx = document.getElementById('visitorChart');
                if (visitorCtx) {
                    if (visitorChartInstance !== null) {
                        visitorChartInstance.destroy();
                    }
                    let datasets = data.data.map(seller => ({
                        label: seller.name,
                        data: seller.sales,
                        borderColor: getRandomColor(),
                        borderWidth: 2,
                    }));
                    
                    visitorChartInstance = new Chart(visitorCtx, {
                        type: 'line',
                        data: {
                            labels: data.labels,
                            datasets: datasets,
                        },
                        options: {responsive: true}
                    });
                }
            })
}

function genTicketChart() {
    fetch('genTicketChart') // Fetch data from the Servlet
            .then(response => response.json()) // Convert response to JSON
            .then(data => {
                // Unique Visitor Line Chart
                const visitorCtx = document.getElementById('visitorChart');
                if (visitorCtx) {
                    if (visitorChartInstance !== null) {
                        visitorChartInstance.destroy();
                    }
                    let datasets = data.data.map(seller => ({
                        label: seller.name,
                        data: seller.sales,
                        borderColor: getRandomColor(),
                        borderWidth: 2,
                    }));
                    
                    visitorChartInstance = new Chart(visitorCtx, {
                        type: 'line',
                        data: {
                            labels: data.labels,
                            datasets: datasets,
                        },
                        options: {responsive: true}
                    });
                }
            })
}

function getRandomColor(alpha = 1) {
    let r = Math.floor(Math.random() * 255);
    let g = Math.floor(Math.random() * 255);
    let b = Math.floor(Math.random() * 255);
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}