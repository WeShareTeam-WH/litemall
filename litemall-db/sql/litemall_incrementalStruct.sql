/**
The following script creates a stored procedure 
that allows a column to be added to a table but only if it does not already exist:
**/
drop procedure if exists AddColumnUnlessExists;
create procedure AddColumnUnlessExists(
    IN dbName tinytext,
    IN tableName tinytext,
    IN fieldName tinytext,
    IN fieldDef text)
begin
    IF NOT EXISTS (
        SELECT * FROM information_schema.COLUMNS
        WHERE column_name=fieldName
        and table_name=tableName
        and table_schema=dbName
        )
    THEN
        set @ddl=CONCAT('ALTER TABLE ',dbName,'.',tableName,
            ' ADD COLUMN ',fieldName,' ',fieldDef);
        prepare stmt from @ddl;
        execute stmt;
    END IF;
end;

-- Add link for each category
call AddColumnUnlessExists('litemall', 'litemall_category', 'link', 'varchar(255) null');
