# Android 界面组件实验报告与代码实现

本仓库（`Android_Test3`）包含针对 Android 界面组件的系列实验实现，涵盖 ListView、AlertDialog、菜单系统、ActionMode 上下文菜单等核心组件的用法，并附加 RecyclerView与CardView的自学内容。所有实验均采用 Kotlin 语言开发，基于 AndroidX 生态，代码结构清晰，可直接运行验证。


## 实验目标

本系列实验旨在掌握 Android 常用界面组件的使用，包括：  
1. ListView 与 SimpleAdapter 的数据绑定及交互  
2. 自定义布局的 AlertDialog 构建与事件处理  
3. XML 定义菜单及菜单交互逻辑实现  
4. ActionMode 上下文菜单的创建与列表多选操作  
5. 自学 RecyclerView与CardView的基本使用  


## 实验内容与代码实现

### 实验1：Android ListView 的用法（对应项目 Test3.1）

#### 实验要求  
- 使用 `SimpleAdapter` 实现列表展示，自定义列表项布局  
- 列表项图片使用指定资源（QQ群附件资源）  
- 点击列表项时通过 Toast 显示选中信息  
- 点击后发送系统通知（图标为应用图标，标题为列表项内容，内容自定义）  


#### 实现细节  
1. **列表布局与数据绑定**  
   - 列表项布局（`item_layout.xml`）：横向排列 `ImageView`（展示动物图片）和 `TextView`（展示动物名称），通过 `layout_weight` 控制比例。  
   - 数据源：在 `MainActivity` 中通过 `initData()` 方法初始化列表数据，存储为 `List<Map<String, Any>>`（键为 `image` 和 `name`，分别对应图片资源ID和动物名称）。  
   - 适配器：使用 `SimpleAdapter` 绑定数据与布局，指定 `from`（数据源键名）和 `to`（布局控件ID）的映射关系。  

2. **点击事件处理**  
   - 为 ListView 设置 `onItemClickListener`，通过 `position` 获取选中项数据，用 `Toast.makeText()` 显示“你选择了：XX”。  

3. **通知功能实现**  
   - 兼容 Android 8.0+ 通知渠道机制：在 `sendNotification()` 中，通过 `NotificationManager` 创建渠道（ID为 `animal_channel`，名称为“动物通知”）。  
   - 通知构建：使用 `NotificationCompat.Builder`，设置小图标（`android.R.drawable.ic_notification_overlay`）、标题（选中的动物名称）、内容（“你点击了该动物项”），并通过 `notify()` 发送（通知ID为1）。  


#### 核心文件  
- `app/src/main/java/com/example/test31/MainActivity.kt`（列表初始化、点击事件、通知发送）  
- `app/src/main/res/layout/item_layout.xml`（列表项布局）  
- `app/src/main/res/layout/activity_main.xml`（主布局，包含 ListView）  


### 实验2：创建自定义布局的 AlertDialog（对应项目 Test3.2）

#### 实验要求  
- 设计自定义登录对话框布局（包含用户名输入框、密码输入框、“登录”和“取消”按钮）  
- 使用 `AlertDialog.Builder` 的 `setView()` 方法加载自定义布局  
- 实现对话框按钮的交互逻辑  


#### 实现细节  
1. **自定义对话框布局**  
   - 布局文件（`dialog_login.xml`）：垂直排列 `EditText`（用户名，hint为“请输入用户名”）、`EditText`（密码，`inputType` 设为 `textPassword`）、水平排列的两个按钮（“取消”和“登录”）。  

2. **对话框构建与显示**  
   - 在 `MainActivity` 中，通过 `LayoutInflater` 加载自定义布局，获取输入框和按钮控件。  
   - 使用 `AlertDialog.Builder` 构建对话框，调用 `setView(view)` 绑定布局，设置标题为“登录”。  

3. **按钮交互逻辑**  
   - “登录”按钮：获取输入框内容，若用户名或密码为空则提示“用户名或密码不能为空”，否则通过 Toast 显示“用户名：XX，密码：XX”，并调用 `dialog.dismiss()` 关闭对话框。  
   - “取消”按钮：直接调用 `dialog.dismiss()` 关闭对话框。  


#### 核心文件  
- `app/src/main/java/com/example/test32/MainActivity.kt`（对话框初始化与事件处理）  
- `app/src/main/res/layout/dialog_login.xml`（自定义对话框布局）  
- `app/src/main/res/layout/activity_main.xml`（主布局，包含触发对话框的按钮）  


### 实验3：使用 XML 定义菜单（对应项目 Test3.3）

#### 实验要求  
- 通过 XML 定义选项菜单，包含三类菜单项：  
  - 字体大小（子菜单：小（10sp）、中（16sp）、大（20sp））  
  - 普通菜单项（点击弹出 Toast 提示）  
  - 字体颜色（子菜单：红色、黑色）  
- 点击菜单项时动态修改文本视图（TextView）的样式  


#### 实现细节  
1. **菜单 XML 定义**  
   - 菜单资源（`menu_main.xml`）：根标签为 `menu`，包含三个 `item`：  
     - 字体大小（`title="字体大小"`，`hasSubMenu="true"`），子菜单包含“小”“中”“大”三个项（`id` 分别为 `small`、`medium`、`large`）。  
     - 普通菜单项（`id="normal_item"`，`title="普通菜单项"`）。  
     - 字体颜色（`title="字体颜色"`，`hasSubMenu="true"`），子菜单包含“红色”“黑色”两个项（`id` 分别为 `red`、`black`）。  

2. **菜单加载与交互**  
   - 重写 `onCreateOptionsMenu()`：通过 `menuInflater.inflate(R.menu.menu_main, menu)` 加载菜单。  
   - 重写 `onOptionsItemSelected()`：根据菜单项 `id` 处理逻辑：  
     - 字体大小：修改 `TextView.textSize`（10f、16f、20f 单位为 sp）。  
     - 普通菜单项：Toast 提示“你点击了普通菜单项”。  
     - 字体颜色：修改 `TextView.textColor`（`Color.RED` 或 `Color.BLACK`）。  


#### 核心文件  
- `app/src/main/java/com/example/test33/MainActivity.kt`（菜单加载与点击事件处理）  
- `app/src/main/res/menu/menu_main.xml`（菜单XML定义）  
- `app/src/main/res/layout/activity_main.xml`（主布局，包含用于样式修改的 TextView）  


### 实验4：创建 ActionMode 上下文菜单（对应项目 Test3.4）

#### 实验要求  
- 使用 ListView 创建列表，长按列表项进入 ActionMode 上下文菜单模式  
- 支持多选列表项，ActionMode 标题显示选中数量  
- 实现“删除”功能，批量删除选中项  


#### 实现细节  
1. **列表与数据模型**  
   - 数据模型：`Item` 类（Java），包含 `text`（文本内容）和 `image`（图片资源ID）。  
   - 自定义适配器 `CustomAdapter`：继承 `BaseAdapter`，重写 `getView()` 方法，根据 `selectedPositions`（记录选中项位置的集合）动态修改列表项背景（选中时为灰色，未选中时为白色）。  

2. **ActionMode 启动与回调**  
   - 长按 ListView 项时启动 ActionMode：通过 `listView.setOnItemLongClickListener` 触发 `startActionMode(callback)`。  
   - `ActionMode.Callback` 实现：  
     - `onCreateActionMode()`：加载上下文菜单（`context_menu.xml`，包含“删除”项），初始化 `selectedPositions`。  
     - `onPrepareActionMode()`：更新标题为“已选择 X 项”。  
     - `onActionItemClicked()`：处理“删除”点击，从后往前遍历 `selectedPositions` 移除数据（避免位置错乱），刷新适配器并关闭 ActionMode。  
     - `onDestroyActionMode()`：清空 `selectedPositions`，刷新列表。  

3. **多选逻辑**  
   - 点击列表项时，若处于 ActionMode 模式，则切换项的选中状态（添加/移除 `selectedPositions`），并更新 ActionMode 标题。  


#### 核心文件  
- `app/src/main/java/com/example/test34/MainActivity.kt`（列表初始化、ActionMode 逻辑）  
- `app/src/main/java/com/example/test34/CustomAdapter.kt`（自定义适配器，处理选中状态）  
- `app/src/main/java/com/example/test34/Item.java`（列表项数据模型）  
- `app/src/main/res/menu/context_menu.xml`（ActionMode 菜单定义）  


### 自学内容：RecyclerView 与 Card-Based Layout

#### 学习目标  
- 理解 RecyclerView作为更灵活的列表控件，相比ListView的优势（复用机制、布局管理器支持等）。  
- 掌握RecyclerView的基本使用：`Adapter`、`ViewHolder`、`LayoutManager` 的配置。  
- 学习CardView的使用，实现卡片式布局，增强界面层次感。  


#### 学习资源  
- **RecyclerView 官方指南**：[https://developer.android.google.cn/guide/topics/ui/layout/recyclerview.html](https://developer.android.google.cn/guide/topics/ui/layout/recyclerview.html)  
- **官方示例**：[https://github.com/android/views-widgets-samples](https://github.com/android/views-widgets-samples)（包含RecyclerView使用示例）  
- **CardView 指南**：[https://developer.android.google.cn/guide/topics/ui/layout/cardview.html#AddDependency](https://developer.android.google.cn/guide/topics/ui/layout/cardview.html#AddDependency)  


#### 核心要点  
1. **RecyclerView 核心组件**  
   - `Adapter`：负责数据绑定，创建 `ViewHolder` 并绑定数据。  
   - `ViewHolder`：缓存列表项视图，减少 findViewById 开销。  
   - `LayoutManager`：控制列表布局方式（线性、网格、瀑布流等）。  

2. **CardView 使用**  
   - 添加依赖：`implementation "androidx.cardview:cardview:1.0.0"`。  
   - 作为列表项根布局，通过 `cardCornerRadius` 设置圆角，`cardElevation` 设置阴影，实现卡片效果。  


## 环境要求  

- Android Studio 版本：2023.1.1 及以上  
- 编译 SDK 版本：36  
- 最小支持 SDK 版本：24（Android 7.0）  
- Kotlin 版本：2.0.21  
- 依赖管理：使用 `gradle/libs.versions.toml` 统一管理（包含 AndroidX 核心库、AppCompat 等）  


## 运行指南  

1. 克隆仓库到本地：  
   ```bash
   git clone https://github.com/HJC1224/Android_Test3.git
   ```  

2. 打开 Android Studio，选择“Open an existing project”，导航至克隆的仓库目录，选择对应子项目（如 `Test3.1`）。  

3. 等待 Gradle 同步完成（首次打开需下载依赖，确保网络通畅）。  

4. 连接 Android 设备（开启 USB 调试）或启动模拟器，点击工具栏的“Run”按钮（▶️）运行项目。  


## 总结  

本仓库通过四个子项目完整实现了 Android 界面组件的核心实验，涵盖列表展示、对话框交互、菜单系统、上下文操作等场景，代码遵循 Android 开发最佳实践，适配主流系统版本。自学内容补充了现代列表控件 RecyclerView与卡片布局的使用，为后续复杂界面开发奠定基础。
