DO $$ 
BEGIN
    RAISE NOTICE 'Creating schema airbnb_clone...';
    CREATE SCHEMA IF NOT EXISTS airbnb_clone;
    RAISE NOTICE 'Schema airbnb_clone created successfully.';
END $$;