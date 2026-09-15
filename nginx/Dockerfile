FROM nginx:alpine
COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/conf.d/eventapp.conf /etc/nginx/conf.d/eventapp.conf
RUN rm /etc/nginx/conf.d/default.conf
COPY frontend/public /usr/share/nginx/html
