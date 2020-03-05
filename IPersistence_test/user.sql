
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

INSERT INTO `user` VALUES (1, 'lucy', '123', '2019-12-12');
INSERT INTO `user` VALUES (2, 'tom', '123', '2019-12-12');
INSERT INTO `user` VALUES (3, 'jack', '1001', NULL);
