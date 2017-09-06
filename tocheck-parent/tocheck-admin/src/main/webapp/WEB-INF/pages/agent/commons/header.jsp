<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Header starts -->
<header>
    <div class="container">
        <div class="row">
            <!-- Logo section -->
            <div class="col-md-4">
                <!-- Logo. -->
                <div class="logo">
                    <h1><a href="#">tocheck<span class="bold"></span></a></h1>
                    <p class="meta">后台管理系统</p>
                </div>
                <!-- Logo ends -->
            </div>
            <!-- Data section -->
            <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
                <!-- Links -->
                <ul class="nav navbar-nav pull-right">
                    <li class="dropdown pull-right">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <i class="icon-user"></i>${agentUser.userName} <b class="caret"></b>
                        </a>

                        <!-- Dropdown menu -->
                        <ul class="dropdown-menu">
                            <li><a href="/agent/info.html"><i class="icon-user"></i> 个人信息</a></li>
                            <li><a href="/agent/exit.html"><i class="icon-off"></i> 退出</a></li>
                        </ul>
                    </li>

                </ul>
            </nav>

        </div>
    </div>
</header>
