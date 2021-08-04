create schema if not exists store_inventory;

create table if not exists store_inventory.product(
  id bigserial primary key,
  product_code varchar,
  product_description varchar,
  created_on timestamp,
  modified_on timestamp
);

create table if not exists store_inventory.inventory(
  id bigserial primary key,
  prod_id bigint references store_inventory.product(id),
  quantity decimal,
  created_on timestamp,
  modified_on timestamp
);

create table if not exists store_inventory.warehouse(
  id bigserial primary key,
  warehouse_code varchar,
  warehouse_description varchar,
  created_on timestamp,
  modified_on timestamp
);

drop table store_inventory.product_warehouse;
create table if not exists store_inventory.product_warehouse(
  id bigserial primary key,
  prod_id bigint,
  warehouse_id bigint,
  warehouse_stock decimal,
  created_on timestamp,
  modified_on timestamp
);

create schema if not exists store_billing_report;

create table if not exists store_billing_report.billing_report(
  id bigserial primary key,
  customer_name varchar,
  created_on timestamp,
  total_quantity decimal,
  total_price decimal,
  discount_percentage decimal
);

create table if not exists store_billing_report.billed_product(
  id bigserial primary key,
  billing_report_id bigint references store_billing_report.billing_report(id),
  prod_id bigint references store_inventory.product(id)
);

create table store_inventory.stock_log(
    id bigserial primary key,
    prod_id bigint references store_inventory.product(id),
    warehouse_id bigint references store_inventory.warehouse(id),
    log_detail varchar,
    created_on timestamp
);

alter table store_inventory.product add column product_selling_price decimal;