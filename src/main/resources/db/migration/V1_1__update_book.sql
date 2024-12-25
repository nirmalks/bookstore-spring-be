ALTER TABLE book ADD COLUMN image_path VARCHAR(255);
ALTER TABLE book ADD COLUMN description TEXT;

UPDATE book
SET image_path = 'the_alchemist.jpg',
    description = 'A philosophical novel by Paulo Coelho, inspiring readers to follow their dreams.'
WHERE title = 'The Alchemist';

UPDATE book
SET image_path = 'harry_potter.jpg',
    description = 'The first book in J.K. Rowling''s Harry Potter series, introducing the magical world of Hogwarts.'
WHERE title = 'Harry Potter and the Sorcerer''s Stone';
