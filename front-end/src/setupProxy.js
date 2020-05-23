const { createProxyMiddleware } = require('http-proxy-middleware');


const filter = function (pathName, req) {

  if (pathName.startsWith('/locationCollection') ||
    req.rawHeaders.includes('application/hal+json') ||
    pathName === '/index') {

    return true;
  }
  return false;

};

const apiProxy = createProxyMiddleware(filter, {
  target: 'http://localhost:8080',
});

module.exports = function (app) {
  app.use('/', apiProxy);
};
