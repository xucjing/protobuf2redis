
DROP TABLE IF EXISTS tb_deliver_address CASCADE;
CREATE TABLE tb_deliver_address (
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键id',
	uid varchar(32) NOT NULL COMMENT '用户唯一标识',
	name varchar(24) NOT NULL COMMENT '收货人',
	address varchar(200) NOT NULL COMMENT '收货地址',
	mobile BIGINT NOT NULL COMMENT '联系方式',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_deliver_address(uid, name, address, mobile) VALUES ('zhangsan', '我的姐姐', '湖北省武汉市江汉区发展大道185号', 12345678910);
INSERT INTO tb_deliver_address(uid, name, address, mobile) VALUES ('zhangsan', '我的妹妹', '安徽省合肥市经济技术开发区芙蓉路678号', 12345678911);
INSERT INTO tb_deliver_address(uid, name, address, mobile) VALUES ('zhangsan', '我的弟弟', '浙江省杭州市滨江区江南大道龙湖滨江天街', 12345678912);
INSERT INTO tb_deliver_address(uid, name, address, mobile) VALUES ('zhangsan', '张三', '广东省佛山市顺德区大良迎宾路碧桂园·钻石湾', 12345678913);