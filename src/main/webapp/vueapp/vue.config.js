const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../resources',
  devServer: {
    port: 8081,                                               //8080포트에서는 요청시 오류가 잦으므로 포트 번호 변경을 해줌
    proxy: {
      '/api': {
        target: 'http://localhost:9420/VueProject-Basic-BO',  // 백엔드 서버 URL
        //ws: true,                        // 웹소켓 지원 여부
        changeOrigin: true,               // 도메인 간 요청 가능
        pathRewrite: {'^/api' : ''}
      }
    }
  }
})