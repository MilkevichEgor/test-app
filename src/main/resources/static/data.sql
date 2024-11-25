-- Вставка департаментов
insert into public.departments (title) values('HR');
insert into public.departments (title) values('IT');
insert into public.departments (title) values('SALES');

	-- Вставка ролей
insert into public.roles (role_name) values ('ROLE_ADMIN');
insert into public.roles (role_name) values ('ROLE_USER');
insert into public.roles (role_name) values ('ROLE_DIRECTOR');

	-- Вставка пользователей
insert into public.users (username, password, first_name, last_name, department_id) values ('egor', '{bcrypt}$2a$10$3ei8.QE7z1jXx5jXvPqviuEUqS5r692sE.sNarKMeOI6NY78WKqHm', 'egor', 'ivanov', 1);
insert into public.users (username, password, first_name, last_name, department_id) values ('vlad', '{bcrypt}$2a$10$B8k5typN9344M8KOOh2sGuU2a3hGC5l0IyDE3Fsm/J2FzjMu6RKqK', 'vladislav', 'petrov', 2);
insert into public.users (username, password, first_name, last_name, department_id) values ('alex', '{bcrypt}$2a$10$Cd9mdlxDv/nbyJSR2AtR8.dOLif2nUAX4UR6wG3VEwhT2.e4/Jsn.', 'alexey', 'stepnov', 2);
insert into public.users (username, password, first_name, last_name, department_id) values ('vladimir', '{bcrypt}$2a$10$FnCvTPIoDJ4AcWoi4tS3hOD0dztxTjM4/RITMPOjgHw8oWcqBIjHi', 'vladimir', 'vladimirov', 3);
insert into public.users (username, password, first_name, last_name, department_id) values ('stepan', '{bcrypt}$2a$10$wGrF/s0mrpCc4LI7i7lH6epDK10DAQRkXup58rdMNE7VK5C8NyLMe', 'stepan', 'safronov', 3);
insert into public.users (username, password, first_name, last_name, department_id) values ('artem', '{bcrypt}$2a$10$h/gWpoPV3UsBucR8/UGcJOAEgNX/0b94fsOlptHNBOCFILSABnX.q', 'artem', 'rebrov', 2);

	-- Вставка ролей для пользователей
insert into public.user_roles (user_id, role_id) values (1, 1);  -- egor -> ROLE_ADMIN
insert into public.user_roles (user_id, role_id) values (2, 2);  -- vlad -> ROLE_USER
insert into public.user_roles (user_id, role_id) values (3, 2);  -- alex -> ROLE_USER
insert into public.user_roles (user_id, role_id) values (4, 3);  -- vladimir -> ROLE_DIRECTOR
insert into public.user_roles (user_id, role_id) values (5, 3);  -- artem -> ROLE_DIRECTOR

-- Назначение директора департамента
update public.departments set director_id = 2 where id = 3; -- vladimir (директор) в SALES
update public.departments set director_id = 3 where id = 2; -- stepan (директор) в IT
update public.departments set director_id = 4 where id = 1; -- artem (директор) в HR
