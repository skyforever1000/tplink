create database tplink;
use tplink;
CREATE USER 'tplink'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON tplink.* TO 'tplink'@'localhost' WITH GRANT OPTION;

CREATE TABLE `group` (
  `_groupId` int(11) NOT NULL AUTO_INCREMENT,
  `_groupName` varchar(100) NOT NULL,
  `_status` int(11) NOT NULL DEFAULT '1',
  `_versionId` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`_groupId`),
  UNIQUE KEY `_groupId_UNIQUE` (`_groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `User` (
  `_userId` int(11) NOT NULL AUTO_INCREMENT,
  `_phoneNo` varchar(50) NOT NULL,
  `_firstName` varchar(100) NOT NULL,
  `_lastName` varchar(100) DEFAULT NULL,
  `_status` int(11) NOT NULL DEFAULT '1',
  `_address` varchar(200) DEFAULT NULL,
  `_groupId` int(11) NOT NULL,
  `_versionId` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`_userId`),
  UNIQUE KEY `_userId_UNIQUE` (`_userId`),
  KEY `_groupId_idx` (`_groupId`),
  CONSTRAINT `_groupId` FOREIGN KEY (`_groupId`) REFERENCES `Group` (`_groupId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

insert into tplink.group (_groupId, _groupName, _status, _versionId) values(default, 'family', default, default);
insert into tplink.group  (_groupId, _groupName, _status, _versionId) values(default, 'friend', default, default);
insert into tplink.group  (_groupId, _groupName, _status, _versionId)  values(default, 'work', default, default);

insert into tplink.User values(null, '650-960-5771', 'John', 'Zhang', default, '452 elm dr', 13, default);
insert into tplink.User values(null, '650-960-5772', 'Ben', 'Chen', default, '2200 rubino dr', 13, default);
insert into tplink.User values(null, '650-960-5773', 'Chris', 'Yuan', default, '1302 el moro', 13, default);
insert into tplink.User values(null, '650-960-5774', 'Mike', 'Lee', default, '550 s 9th st', 14, default);
insert into tplink.User values(null, '650-960-5775', 'Curry', 'Lau', default, '768 de anza',14, default);
insert into tplink.User values(null, '650-960-5776', 'Jessie', 'Wang', default, '4600 rosewood dr', 15, default);


