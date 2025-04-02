
# Java + Datadog APM + OpenTracing 範例操作說明

本指南說明如何在 Terminal 中編譯並執行一個整合 Datadog Java Agent 和 OpenTracing 的 Java 程式。

---

## 📌 先備知識

- 你可以在 Terminal 使用 `Control + R` 快速搜尋你之前執行過的指令
- 可以使用 `wget` 從網路上下載所需的 `.jar` 檔案
- 預設下載的檔案會儲存在當前工作目錄

---

## ✅ 步驟說明

### 1. 下載所需的 JAR 檔案

在 Terminal 中執行以下指令，下載 OpenTracing 和 Datadog 所需的 `.jar` 檔案：

```bash
wget https://repo1.maven.org/maven2/io/opentracing/opentracing-api/0.33.0/opentracing-api-0.33.0.jar
wget https://repo1.maven.org/maven2/io/opentracing/opentracing-util/0.33.0/opentracing-util-0.33.0.jar
wget https://repo1.maven.org/maven2/io/opentracing/opentracing-noop/0.33.0/opentracing-noop-0.33.0.jar
wget https://repo1.maven.org/maven2/com/datadoghq/dd-trace-api/1.26.0/dd-trace-api-1.26.0.jar
```

> ✅ **提示**：請確保你也已經下載好 `dd-java-agent.jar`（Datadog Java Agent）

---

### 2. 編譯 Java 檔案

使用 `javac` 編譯你的程式碼，並加入相依的 `.jar` 檔案路徑：

```bash
javac \
  -cp ".:dd-trace-api-1.26.0.jar:opentracing-api-0.33.0.jar:opentracing-util-0.33.0.jar:opentracing-noop-0.33.0.jar" \
  HttpGetExample.java
```

---

### 3. 執行 Java 程式，啟用 Datadog Agent

使用 `java` 搭配 `-javaagent` 參數載入 Datadog Agent 並執行你的程式：

```bash
java \
  -javaagent:dd-java-agent.jar \
  -Ddd.profiling.enabled=true \
  -XX:FlightRecorderOptions=stackdepth=256 \
  -cp ".:dd-trace-api-1.26.0.jar:opentracing-api-0.33.0.jar:opentracing-util-0.33.0.jar:opentracing-noop-0.33.0.jar" \
  HttpGetExample
```

---

## 📂 檔案結構建議

```plaintext
.
├── dd-java-agent.jar
├── dd-trace-api-1.26.0.jar
├── opentracing-api-0.33.0.jar
├── opentracing-util-0.33.0.jar
├── opentracing-noop-0.33.0.jar
├── HttpGetExample.java
├── HttpGetExample.class
└── README.md
```

```bash
$ ls
HttpGetExample.class  HttpGetExample.java  dd-java-agent.jar  dd-trace-api-1.26.0.jar  ddagent-install.log  opentracing-api-0.33.0.jar  opentracing-noop-0.33.0.jar  opentracing-util-0.33.0.jar
```

---

## 🧪 測試效果

當你成功執行後，程式將會：

- 發送一個 HTTP GET 請求到 https://www.google.com
- 透過 `@Trace` 標註的區塊產生 Datadog 的 tracing 資訊
- 將自定義的 `span tag` 資訊（如 customer.id、cart.value 等）送至 Datadog APM
- 若已連接 Datadog Agent，Trace 應會自動被收集


## 參考連結
https://docs.datadoghq.com/tracing/trace_collection/custom_instrumentation/java/dd-api