/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const sidebar = document.getElementById("sidebar");
    const toggleBtn = document.getElementById("toggle-btn");
    const sidepane = document.getElementById("sidepane");

    if (sidebar && toggleBtn) {
        toggleBtn.addEventListener("click", function () {
            sidebar.classList.toggle("collapsed");
            if (sidebar.classList.contains("collapsed")) {
                sidepane.style.display = "none";
            } else {
                sidepane.style.display = "block";
            }
        });
    }
});
