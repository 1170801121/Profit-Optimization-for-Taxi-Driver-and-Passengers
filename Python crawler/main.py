import re
import urllib.request
from bs4 import BeautifulSoup, Tag

attr_list = ["机场", "起飞/到达", "航线名", "机型", "每周班期", "参考准点率", "航空公司/航班号", "价格排序", "出发时间"]
parsed = []


class Data(object):

    @staticmethod
    def parse(html_element):
        func_dict = {}
        func_dict[9] = Data.week_parser
        func_dict[15] = Data.price_parser

        data = []

        i = 0
        attrs = html_element.contents
        for attr in attrs:
            if type(attr) is Tag:
                data.append(func_dict.get(i, Data.default_parser)(attr))
            i += 1

        return ','.join(data) + '\n'

    @staticmethod
    def default_parser(attr_element):
        res = []
        for mini in attr_element.children:
            if mini.string is not None:
                tmp = re.sub('\s', '', mini.string)
                if tmp is not '':
                    res.append(tmp)
        return ';'.join(res)

    @staticmethod
    def week_parser(attr_element):
        res = []
        for mini in attr_element.children:
            if type(mini) is Tag:
                _class = mini.attrs.get('class', [''])
                res.append('1' if _class[0] != 'none' else '0')
        return ''.join(res)

    @staticmethod
    def price_parser(attr_element):
        '<[^>]*>'
        res = []
        for mini in attr_element.children:
            text = mini.text if type(mini) is Tag else mini.string
            tmp = re.sub('\s', '', text)
            if tmp is not '':
                res.append(tmp)
        return ';'.join(res)



if __name__ == '__main__':
    print(','.join(attr_list))
    base_url = "https://flights.ctrip.com/schedule/departairport-pudong/inmap-{0}.html"
    for page_num in range(1, 47):
        print("Parsing "+ str(page_num))
        url = base_url.format(page_num)
        resp = urllib.request.urlopen(url)
        html = resp.read()
        bs = BeautifulSoup(html, "html.parser")
        form = bs.find('tbody', id='flt1')
        page_num = 0

        entries = form.contents
        for entry in entries:
            if type(entry) is Tag:
                parsed.append(Data.parse(entry))
    with open('data.csv', 'w',encoding='utf8') as f:
        f.writelines(parsed)
