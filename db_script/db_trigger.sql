DELIMITER $$
DROP TRIGGER IF EXISTS `before_group_update` $$
CREATE TRIGGER before_group_update
before UPDATE ON tplink.group
FOR EACH ROW
BEGIN
if New._status = 0 then 
update tplink.User set _status = 0 where _groupId = New._groupId;
end if;
END$$