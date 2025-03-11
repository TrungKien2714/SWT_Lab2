<nav class="sb-topnav navbar navbar-expand navbar-light bg-white shadow-sm">
    <!-- Navbar Brand -->
    <a class="navbar-brand ps-3 text-primary fw-bold" href="../staff_page/staffDashboard">
        <i class="fas fa-rocket"></i> Hello, Staff
    </a>

    <!-- Sidebar Toggle -->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0 text-primary" id="sidebarToggle" href="#!">
        <i class="fas fa-bars"></i>
    </button>

    <!-- Navbar Search -->
    <form class="d-none d-md-inline-block form-inline ms-auto me-3 my-2 my-md-0">
        <div class="input-group">
            <input class="form-control border-0 shadow-sm" type="text" placeholder="Search for..." aria-label="Search for..." />
            <button class="btn btn-primary" id="btnNavbarSearch" type="button">
                <i class="fas fa-search"></i>
            </button>
        </div>
    </form>


        <!-- Avatar & Dropdown -->
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle d-flex align-items-center" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img src="assets/img/user-avatar.jpg" alt="Profile" class="rounded-circle" width="30" height="30">
            </a>
            <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="#!"><i class="fas fa-cog"></i> Settings</a></li>
                <li><a class="dropdown-item" href="#!"><i class="fas fa-list"></i> Activity Log</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item text-danger" href="../logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
            </ul>
        </li>
    </ul>
</nav>
