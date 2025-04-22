# 測試專案SpringBoot3 + Feign的專案

**啟動Service**

- 啟動Server端**: ` ./gradlew :feign-provider:bootRun `
- 啟動Client端: ` ./gradlew :feign-consumer:bootRun `

**測試不同的場景**：

1. 訪問 `curl http://localhost:8082/consumer/test1` 測試正常值
2. 訪問 `curl http://localhost:8082/consumer/test2` 測試null值
3. 訪問 `curl http://localhost:8082/consumer/test3` 測試空字符串
4. 訪問 `curl http://localhost:8082/consumer/test4` 測試Header非空&Param空字串

**觀察日誌輸出**：

通過觀察日誌輸出，您可以清楚地看到Spring Boot 3中FeignClient對@RequestHeader和@RequestParam的處理行為。這將幫助您理解空值是如何被處理的，並根據需要調整您的代碼。

