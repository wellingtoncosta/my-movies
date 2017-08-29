## Sql-based migrations:

        # Typical usage
        * DDL changes (CREATE/ALTER/DROP statements for TABLES,VIEWS,TRIGGERS,SEQUENCES,...)
        * Simple reference data changes (CRUD in reference data tables)
        * Simple bulk data changes (CRUD in regular data tables)

        # Location and discovery
        Sql migrations reside on the classpath or on the file system in one or more directories referenced by the locations property.
        Unprefixed locations or locations with the classpath: prefix target the classpath.
        Locations with the filesystem: prefix target the file system.

        New sql migrations are discovered automatically through classpath and file system scanning at runtime.
        This scanning is recursive. All migrations in directories below the specified ones are also picked up.

        # Naming

        In order to be picked by the classpath scanner, the sql migrations must follow a naming pattern:

        Version Migration

        V2__Add_new_table.sql

        Where:
        * Prefix: V
        * Version: Number
        * Descrition: Text
        * Suffix: .sql


        The file name consists of:

        * prefix: Configurable, default: V
        * version: Dots or underscores separate the parts, you can use as many parts as you like
        * separator: Configurable, default: __ (two underscores)
        * description: Underscores or spaces separate the words
        * suffix: Configurable, default: .sql