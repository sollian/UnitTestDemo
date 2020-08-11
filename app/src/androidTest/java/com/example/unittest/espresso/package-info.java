/**
 * instrument test 仪器测试（在虚拟机或真机运行）
 * 主要是google的espresso框架
 *
 * 两部分组成：
 * 界面交互
 * View元素断言
 *
 * 三个重要类：
 * ViewMatchers：提供各种匹配器。使用的是hamcrest匹配器
 * ViewActions：提供各种界面行为
 * ViewAssertions：提供各种界面判断
 *
 * espresso测试记录器：
 * 使用时请关闭动画，否则可能出现意外结果
 * 在开发人员选项中关闭：
 * 1、窗口动画比例
 * 2、过渡动画比例
 * 3、Animator时长缩放
 *
 * 软件包：
 * espresso-core - 包含核心和基本的 View 匹配器、操作和断言。请参阅基础知识和测试方案。
 * espresso-web - 包含 WebView 支持的资源。
 * espresso-idling-resource - Espresso 与后台作业同步的机制。
 * espresso-contrib - 外部贡献，包含 DatePicker、RecyclerView 和 Drawer 操作、无障碍功能检查以及 CountingIdlingResource。
 * espresso-intents - 用于对封闭测试的 intent 进行验证和打桩的扩展。
 * espresso-remote - Espresso 的多进程功能的位置。
 *
 * 详细说明参考：
 * <a href="https://developer.android.com/training/testing/espresso"></a>
 */

package com.example.unittest.espresso;