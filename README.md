# incident-management

# 事件管理应用

这是一个简单的事件管理应用，允许用户管理事件。该应用包含一个基于 Spring Boot 的后端和一个基于 React 的前端。用户可以通过 Web 界面添加、修改、删除和查看事件。

## 特性

- **事件管理**：创建、更新、删除和查看事件。
- **内存存储**：数据存储在H2本地内存数据库中，没有持久存储。
- **RESTful API**：后端提供了用于事件管理的 RESTful API。
- **React 前端**：一个简单的 Web 界面，用于与 API 交互。
- **验证和错误处理**：实现了适当的验证和错误处理机制。

## 技术栈

- **后端**：Java, Spring Boot
- **前端**：React
- **构建工具**：Maven（用于 Java），npm（用于 React）
- **其他**：Docker（用于容器化）,k8s(用于集群部署）,jmeter(用于监控)，Actuator（用于监控）,Axios（用于 React 中的 API 请求）

## 快速开始

### 先决条件

- Java 11 或更高版本
- Node.js（v14 或更高版本）和 npm
- Maven

### 后端设置

1. **克隆仓库：**

    ```bash
    git clone <repository-url>
    cd incident-management
    ```

2. **构建后端：**

   使用 Maven 构建 Spring Boot 后端。

    ```bash
    mvn clean package
    ```

3. **运行后端：**

   你可以直接从终端运行 Spring Boot 应用。

    ```bash
    mvn spring-boot:run
    ```

   后端将可在 `http://localhost:8080` 访问。

### 前端设置

1. **导航到前端目录：**

    ```bash
    cd incident-management-frontend
    ```

2. **安装依赖：**

   使用 npm 安装所需的依赖。

    ```bash
    npm install
    ```

3. **运行前端：**

   启动 React 开发服务器。

    ```bash
    npm start
    ```

   前端将可在 `http://localhost:3000` 访问，并将请求代理到后端。

### 使用 Docker

你可以使用 Docker 来容器化应用。

1. **构建 Docker 镜像：**

   在后端目录中：

    ```bash
    docker build -t incident-management .
    ```

2. **运行容器：**

    ```bash
    docker run -p 8080:8080 incident-management
    ```

   现在，后端将可在 `http://localhost:8080` 访问。

## API 端点

后端提供了以下 RESTful API 端点：

- `GET /api/incidents`：列出所有事件。
- `POST /rest/v1/incident/create_incident`：创建一个新事件。
- `PUT /rest/v1/incident/update_incident/{id}`：更新一个现有事件。
- `PUT /rest/v1/incident/delete_incident/{id}`：按 ID 删除一个事件。

### 示例 API 请求

以下是使用 `curl` 创建新事件的示例：

```bash
curl --location --request POST 'http://localhost:8080/rest/v1/incident/create_incident' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "title2",
    "description": "description1",
    "startTime": 1723651200000,
    "endTime": 1723737600000,
    "remark": "remark"
}'

