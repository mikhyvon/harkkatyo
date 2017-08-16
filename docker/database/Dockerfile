FROM postgres:9.5

RUN localedef -i fi_FI -c -f UTF-8 -A /usr/share/locale/locale.alias fi_FI.UTF-8
ENV LANG fi_FI.utf8

RUN rm -f /etc/localtime
RUN ln -s /usr/share/zoneinfo/Europe/Helsinki /etc/localtime
