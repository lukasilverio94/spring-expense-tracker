create table users
(
    user_id    uuid primary key,
    email      varchar(255) not null,
    password   varchar(255) not null,
    created_at timestamp default now(),
    updated_at timestamp
);

create table category
(
    category_id bigserial primary key,
    name        varchar(255) not null,
    user_id     uuid         not null,
    created_at  timestamp default now(),
    updated_at  timestamp,
    foreign key (user_id) references users (user_id) on delete cascade,
    unique (user_id, name)
);

create table expense
(
    expense_id  bigserial primary key,
    description varchar(255),
    date_time        timestamp not null,
    amount      float     not null,
    user_id     uuid      not null,
    category_id bigint    not null,
    created_at  timestamp default now(),
    updated_at  timestamp,
    foreign key (user_id) references users (user_id) on delete cascade,
    foreign key (category_id) references category (category_id) on delete restrict
);

-- indexes for performance
create index idx_expense_user_id ON expense (user_id);
create index idx_expense_category_id ON expense (category_id);
create index idx_category_user_id ON category (user_id);
