CREATE TABLE pfm_postal_code
(
  pfm_postal_code_id bigint NOT NULL DEFAULT 0,
  postal_code character varying(7) NOT NULL DEFAULT ''::character varying,
  prefecture_code character varying(2) NOT NULL DEFAULT ''::character varying,
  city_code character varying(3) NOT NULL DEFAULT ''::character varying,
  city_name character varying(15) NOT NULL DEFAULT ''::character varying,
  city_kana character varying(30) NOT NULL DEFAULT ''::character varying,
  address_name character varying(50) NOT NULL DEFAULT ''::character varying,
  address_kana character varying(100) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL, 
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL, 
  CONSTRAINT pfm_postal_code_pkey PRIMARY KEY (pfm_postal_code_id)
);
COMMENT ON TABLE pfm_postal_code IS '郵便番号マスタ';
COMMENT ON COLUMN pfm_postal_code.pfm_postal_code_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_postal_code.postal_code IS '郵便番号';
COMMENT ON COLUMN pfm_postal_code.prefecture_code IS '都道府県コード';
COMMENT ON COLUMN pfm_postal_code.city_code IS '市区町村コード';
COMMENT ON COLUMN pfm_postal_code.city_name IS '市区町村名';
COMMENT ON COLUMN pfm_postal_code.city_kana IS '市区町村名カナ';
COMMENT ON COLUMN pfm_postal_code.address_name IS '町域名';
COMMENT ON COLUMN pfm_postal_code.address_kana IS '町域名カナ';
COMMENT ON COLUMN pfm_postal_code.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_postal_code.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_postal_code.insert_date IS '登録日';
COMMENT ON COLUMN pfm_postal_code.insert_user IS '登録者';
COMMENT ON COLUMN pfm_postal_code.update_date IS '更新日';
COMMENT ON COLUMN pfm_postal_code.update_user IS '更新者';


CREATE TABLE pfm_bank_base
(
  pfm_bank_base_id bigint NOT NULL DEFAULT 0,
  bank_code character varying(4) NOT NULL DEFAULT ''::character varying,
  bank_name character varying(32) NOT NULL DEFAULT ''::character varying,
  bank_name_kana character varying(15) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL, 
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL, 
  CONSTRAINT pfm_bank_base_pkey PRIMARY KEY (pfm_bank_base_id)
);
COMMENT ON TABLE pfm_bank_base IS '銀行マスタ';
COMMENT ON COLUMN pfm_bank_base.pfm_bank_base_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_bank_base.bank_code IS '銀行コード';
COMMENT ON COLUMN pfm_bank_base.bank_name IS '銀行名';
COMMENT ON COLUMN pfm_bank_base.bank_name_kana IS '銀行名カナ';
COMMENT ON COLUMN pfm_bank_base.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_bank_base.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_bank_base.insert_date IS '登録日';
COMMENT ON COLUMN pfm_bank_base.insert_user IS '登録者';
COMMENT ON COLUMN pfm_bank_base.update_date IS '更新日';
COMMENT ON COLUMN pfm_bank_base.update_user IS '更新者';


CREATE TABLE pfm_bank_branch
(
  pfm_bank_branch_id bigint NOT NULL DEFAULT 0,
  bank_code  character varying(4) NOT NULL DEFAULT ''::character varying,
  branch_code character varying(3) NOT NULL DEFAULT ''::character varying,
  branch_name character varying(50) NOT NULL DEFAULT ''::character varying,
  branch_kana character varying(15) NOT NULL DEFAULT ''::character varying,
  branch_address_name character varying(112) NOT NULL DEFAULT ''::character varying,
  branch_address_kana character varying(80) NOT NULL DEFAULT ''::character varying,
  branch_phone character varying(17) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL, 
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL, 
  CONSTRAINT pfm_bank_branch_pkey PRIMARY KEY (pfm_bank_branch_id)
);
COMMENT ON TABLE pfm_bank_branch IS '銀行支店マスタ';
COMMENT ON COLUMN pfm_bank_branch.pfm_bank_branch_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_bank_branch.bank_code IS '銀行コード';
COMMENT ON COLUMN pfm_bank_branch.branch_code IS '支店コード';
COMMENT ON COLUMN pfm_bank_branch.branch_name IS '支店名';
COMMENT ON COLUMN pfm_bank_branch.branch_kana IS '支店名カナ';
COMMENT ON COLUMN pfm_bank_branch.branch_address_name IS '支店所在地';
COMMENT ON COLUMN pfm_bank_branch.branch_address_kana IS '支店所在地カナ';
COMMENT ON COLUMN pfm_bank_branch.branch_phone IS '支店電話番号';
COMMENT ON COLUMN pfm_bank_branch.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_bank_branch.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_bank_branch.insert_date IS '登録日';
COMMENT ON COLUMN pfm_bank_branch.insert_user IS '登録者';
COMMENT ON COLUMN pfm_bank_branch.update_date IS '更新日';
COMMENT ON COLUMN pfm_bank_branch.update_user IS '更新者';


CREATE TABLE pfa_account
(
  pfa_account_id bigint NOT NULL DEFAULT 0,
  holder_id character varying(20) NOT NULL DEFAULT ''::character varying,
  account_type character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  bank_code character varying(4) NOT NULL DEFAULT ''::character varying,
  bank_name character varying(32) NOT NULL DEFAULT ''::character varying,
  branch_code character varying(3) NOT NULL DEFAULT ''::character varying,
  branch_name character varying(50) NOT NULL DEFAULT ''::character varying,
  account_class character varying(1) NOT NULL DEFAULT ''::character varying,
  account_number character varying(7) NOT NULL DEFAULT ''::character varying,
  account_holder character varying(30) NOT NULL DEFAULT ''::character varying,
  fixed_payment integer NOT NULL DEFAULT 0,
  fixed_bonus integer NOT NULL DEFAULT 0,
  request_type character varying(1) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_account_pkey PRIMARY KEY (pfa_account_id)
)
;
COMMENT ON TABLE pfa_account IS '口座情報';
COMMENT ON COLUMN pfa_account.pfa_account_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_account.holder_id IS '保持者ID';
COMMENT ON COLUMN pfa_account.account_type IS '口座区分';
COMMENT ON COLUMN pfa_account.activate_date IS '有効日';
COMMENT ON COLUMN pfa_account.bank_code IS '銀行コード';
COMMENT ON COLUMN pfa_account.bank_name IS '銀行名';
COMMENT ON COLUMN pfa_account.branch_code IS '支店コード';
COMMENT ON COLUMN pfa_account.branch_name IS '支店名';
COMMENT ON COLUMN pfa_account.account_class IS '口座種別(普通/当座)';
COMMENT ON COLUMN pfa_account.account_number IS '口座番号';
COMMENT ON COLUMN pfa_account.account_holder IS '口座名義';
COMMENT ON COLUMN pfa_account.fixed_payment IS '定額(給与)';
COMMENT ON COLUMN pfa_account.fixed_bonus IS '定額(賞与)';
COMMENT ON COLUMN pfa_account.request_type IS '申請区分';
COMMENT ON COLUMN pfa_account.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN pfa_account.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_account.insert_date IS '登録日';
COMMENT ON COLUMN pfa_account.insert_user IS '登録者';
COMMENT ON COLUMN pfa_account.update_date IS '更新日';
COMMENT ON COLUMN pfa_account.update_user IS '更新者';


CREATE TABLE pfa_address
(
  pfa_address_id bigint NOT NULL DEFAULT 0,
  holder_id character varying(20) NOT NULL DEFAULT ''::character varying,
  address_type character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying,
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  prefecture character varying(2) NOT NULL DEFAULT ''::character varying,
  city_code character varying(3) NOT NULL DEFAULT ''::character varying,
  address character varying(64) NOT NULL DEFAULT ''::character varying,
  address_number character varying(64) NOT NULL DEFAULT ''::character varying,
  building character varying(64) NOT NULL DEFAULT ''::character varying,
  request_type character varying(10) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_address_pkey PRIMARY KEY (pfa_address_id)
)
;
COMMENT ON TABLE pfa_address IS '住所情報';
COMMENT ON COLUMN pfa_address.pfa_address_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_address.holder_id IS '保持者ID';
COMMENT ON COLUMN pfa_address.address_type IS '住所区分';
COMMENT ON COLUMN pfa_address.activate_date IS '有効日';
COMMENT ON COLUMN pfa_address.postal_code_1 IS '郵便番号1';
COMMENT ON COLUMN pfa_address.postal_code_2 IS '郵便番号2';
COMMENT ON COLUMN pfa_address.prefecture IS '都道府県コード';
COMMENT ON COLUMN pfa_address.city_code IS '市区町村コード';
COMMENT ON COLUMN pfa_address.address IS '市区町村';
COMMENT ON COLUMN pfa_address.address_number IS '番地';
COMMENT ON COLUMN pfa_address.building IS '建物情報';
COMMENT ON COLUMN pfa_address.request_type IS '申請区分';
COMMENT ON COLUMN pfa_address.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_address.insert_date IS '登録日';
COMMENT ON COLUMN pfa_address.insert_user IS '登録者';
COMMENT ON COLUMN pfa_address.update_date IS '更新日';
COMMENT ON COLUMN pfa_address.update_user IS '更新者';


CREATE TABLE pfa_human_concurrent
(
  pfa_human_concurrent_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  start_date date NOT NULL,
  end_date date,
  section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  position_code character varying(10) NOT NULL DEFAULT ''::character varying,
  concurrent_remark text NOT NULL DEFAULT ''::text,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_concurrent_pkey PRIMARY KEY (pfa_human_concurrent_id)
)
;
COMMENT ON TABLE pfa_human_concurrent IS '人事兼務情報';
COMMENT ON COLUMN pfa_human_concurrent.pfa_human_concurrent_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_concurrent.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_concurrent.start_date IS '開始日';
COMMENT ON COLUMN pfa_human_concurrent.end_date IS '終了日';
COMMENT ON COLUMN pfa_human_concurrent.section_code IS '所属コード';
COMMENT ON COLUMN pfa_human_concurrent.position_code IS '職位コード';
COMMENT ON COLUMN pfa_human_concurrent.concurrent_remark IS '備考';
COMMENT ON COLUMN pfa_human_concurrent.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_concurrent.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_concurrent.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_concurrent.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_concurrent.update_user IS '更新者';


CREATE TABLE pfa_human_entrance
(
  pfa_human_entrance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  entrance_date date NOT NULL,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_entrance_pkey PRIMARY KEY (pfa_human_entrance_id)
)
;
COMMENT ON TABLE pfa_human_entrance IS '人事入社情報';
COMMENT ON COLUMN pfa_human_entrance.pfa_human_entrance_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_entrance.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_entrance.entrance_date IS '入社日';
COMMENT ON COLUMN pfa_human_entrance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_entrance.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_entrance.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_entrance.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_entrance.update_user IS '更新者';


CREATE TABLE pfa_human_retirement
(
  pfa_human_retirement_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  retirement_date date NOT NULL,
  retirement_reason character varying(10) NOT NULL DEFAULT ''::character varying,
  retirement_detail text NOT NULL DEFAULT ''::text,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_retirement_pkey PRIMARY KEY (pfa_human_retirement_id)
)
;
COMMENT ON TABLE pfa_human_retirement IS '人事退職情報';
COMMENT ON COLUMN pfa_human_retirement.pfa_human_retirement_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_retirement.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_retirement.retirement_date IS '退職日';
COMMENT ON COLUMN pfa_human_retirement.retirement_reason IS '退職理由';
COMMENT ON COLUMN pfa_human_retirement.retirement_detail IS '詳細';
COMMENT ON COLUMN pfa_human_retirement.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_retirement.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_retirement.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_retirement.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_retirement.update_user IS '更新者';


CREATE TABLE pfa_human_suspension
(
  pfa_human_suspension_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  start_date date NOT NULL,
  end_date date,
  schedule_end_date date NOT NULL,
  allowance_type character varying(10) NOT NULL DEFAULT ''::character varying,
  suspension_reason text NOT NULL DEFAULT ''::text,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_suspension_pkey PRIMARY KEY (pfa_human_suspension_id)
)
;
COMMENT ON TABLE pfa_human_suspension IS '人事休職情報';
COMMENT ON COLUMN pfa_human_suspension.pfa_human_suspension_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_suspension.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_suspension.start_date IS '開始日';
COMMENT ON COLUMN pfa_human_suspension.end_date IS '終了日';
COMMENT ON COLUMN pfa_human_suspension.schedule_end_date IS '終了予定日';
COMMENT ON COLUMN pfa_human_suspension.allowance_type IS '給与区分';
COMMENT ON COLUMN pfa_human_suspension.suspension_reason IS '理由';
COMMENT ON COLUMN pfa_human_suspension.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_suspension.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_suspension.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_suspension.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_suspension.update_user IS '更新者';


CREATE TABLE pfa_phone
(
  pfa_phone_id bigint NOT NULL DEFAULT 0,
  holder_id character varying(20) NOT NULL DEFAULT ''::character varying,
  phone_type character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying,
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying,
  request_type character varying(10) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_phone_pkey PRIMARY KEY (pfa_phone_id)
)
;
COMMENT ON TABLE pfa_phone IS '電話情報';
COMMENT ON COLUMN pfa_phone.pfa_phone_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_phone.holder_id IS '保持者ID';
COMMENT ON COLUMN pfa_phone.phone_type IS '電話区分';
COMMENT ON COLUMN pfa_phone.activate_date IS '有効日';
COMMENT ON COLUMN pfa_phone.phone_number_1 IS '電話番号1';
COMMENT ON COLUMN pfa_phone.phone_number_2 IS '電話番号2';
COMMENT ON COLUMN pfa_phone.phone_number_3 IS '電話番号3';
COMMENT ON COLUMN pfa_phone.request_type IS '申請区分';
COMMENT ON COLUMN pfa_phone.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_phone.insert_date IS '登録日';
COMMENT ON COLUMN pfa_phone.insert_user IS '登録者';
COMMENT ON COLUMN pfa_phone.update_date IS '更新日';
COMMENT ON COLUMN pfa_phone.update_user IS '更新者';


CREATE TABLE pfa_user_password
(
  pfa_user_password_id bigint NOT NULL DEFAULT 0,
  user_id character varying(50) NOT NULL DEFAULT ''::character varying,
  change_date date NOT NULL,
  "password" character varying(50) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_user_password_pkey PRIMARY KEY (pfa_user_password_id)
)
;
COMMENT ON TABLE pfa_user_password IS 'ユーザパスワード情報';
COMMENT ON COLUMN pfa_user_password.pfa_user_password_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_user_password.user_id IS 'ユーザID';
COMMENT ON COLUMN pfa_user_password.change_date IS '変更日';
COMMENT ON COLUMN pfa_user_password."password" IS 'パスワード';
COMMENT ON COLUMN pfa_user_password.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_user_password.insert_date IS '登録日';
COMMENT ON COLUMN pfa_user_password.insert_user IS '登録者';
COMMENT ON COLUMN pfa_user_password.update_date IS '更新日';
COMMENT ON COLUMN pfa_user_password.update_user IS '更新者';


CREATE TABLE pfa_user_extra_role
(
  pfa_user_extra_role_id bigint NOT NULL DEFAULT 0,
  user_id character varying(50) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  role_type character varying(10) NOT NULL DEFAULT ''::character varying,
  role_code character varying(10) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_user_extra_role_pkey PRIMARY KEY (pfa_user_extra_role_id)
)
;
COMMENT ON TABLE pfa_user_extra_role IS 'ユーザ追加ロール情報';
COMMENT ON COLUMN pfa_user_extra_role.pfa_user_extra_role_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_user_extra_role.user_id IS 'ユーザID';
COMMENT ON COLUMN pfa_user_extra_role.activate_date IS '有効日';
COMMENT ON COLUMN pfa_user_extra_role.role_type IS 'ロール区分';
COMMENT ON COLUMN pfa_user_extra_role.role_code IS 'ロールコード';
COMMENT ON COLUMN pfa_user_extra_role.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_user_extra_role.insert_date IS '登録日';
COMMENT ON COLUMN pfa_user_extra_role.insert_user IS '登録者';
COMMENT ON COLUMN pfa_user_extra_role.update_date IS '更新日';
COMMENT ON COLUMN pfa_user_extra_role.update_user IS '更新者';


CREATE TABLE pfa_human_normal
(
  pfa_human_normal_id bigint NOT NULL DEFAULT 0, 
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  human_item_type character varying(50) NOT NULL DEFAULT ''::character varying,
  human_item_value text NOT NULL DEFAULT ''::text, 
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_normal_pkey PRIMARY KEY (pfa_human_normal_id)
)
;
COMMENT ON TABLE pfa_human_normal IS '人事汎用通常情報';
COMMENT ON COLUMN pfa_human_normal.pfa_human_normal_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_normal.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_normal.human_item_type IS '人事項目区分';
COMMENT ON COLUMN pfa_human_normal.human_item_value IS '人事項目値';
COMMENT ON COLUMN pfa_human_normal.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_human_normal.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_normal.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_normal.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_normal.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_normal.update_user IS '更新者';


CREATE TABLE pfa_human_history
(
  pfa_human_history_id bigint NOT NULL DEFAULT 0, 
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  human_item_type character varying(50) NOT NULL DEFAULT ''::character varying, 
  activate_date date NOT NULL,
  human_item_value text NOT NULL DEFAULT ''::text,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_history_pkey PRIMARY KEY (pfa_human_history_id)
)
;
COMMENT ON TABLE pfa_human_history IS '人事汎用履歴情報';
COMMENT ON COLUMN pfa_human_history.pfa_human_history_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_history.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_history.human_item_type IS '人事項目区分';
COMMENT ON COLUMN pfa_human_history.activate_date IS '有効日';
COMMENT ON COLUMN pfa_human_history.human_item_value IS '人事項目値';
COMMENT ON COLUMN pfa_human_history.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_human_history.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_history.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_history.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_history.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_history.update_user IS '更新者';


CREATE TABLE pfa_human_array
(
  pfa_human_array_id bigint NOT NULL DEFAULT 0, 
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  human_item_type character varying(50) NOT NULL DEFAULT ''::character varying, 
  row_id integer NOT NULL DEFAULT 0,
  activate_date date NOT NULL,
  human_item_value text NOT NULL DEFAULT ''::text,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_array_pkey PRIMARY KEY (pfa_human_array_id)
)
;
COMMENT ON TABLE pfa_human_array IS '人事汎用一覧情報';
COMMENT ON COLUMN pfa_human_array.pfa_human_array_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_array.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_array.human_item_type IS '人事項目区分';
COMMENT ON COLUMN pfa_human_array.row_id IS '行ID';
COMMENT ON COLUMN pfa_human_array.activate_date IS '有効日';
COMMENT ON COLUMN pfa_human_array.human_item_value IS '人事項目値';
COMMENT ON COLUMN pfa_human_array.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_human_array.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_array.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_array.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_array.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_array.update_user IS '更新者';


CREATE TABLE pfa_human_binary_normal
(
  pfa_human_binary_normal_id bigint NOT NULL DEFAULT 0, 
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  human_item_type character varying(50) NOT NULL DEFAULT ''::character varying,
  human_item_binary bytea,
  file_type character varying(10) NOT NULL DEFAULT ''::character varying,
  file_name character varying(50) NOT NULL DEFAULT ''::character varying,
  file_remark text NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_binary_normal_pkey PRIMARY KEY (pfa_human_binary_normal_id)
)
;
COMMENT ON TABLE pfa_human_binary_normal IS '人事汎用バイナリ通常情報';
COMMENT ON COLUMN pfa_human_binary_normal.pfa_human_binary_normal_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_binary_normal.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_binary_normal.human_item_type IS '人事項目区分';
COMMENT ON COLUMN pfa_human_binary_normal.human_item_binary IS '人事項目バイナリ値';
COMMENT ON COLUMN pfa_human_binary_normal.file_type IS 'ファイル拡張子';
COMMENT ON COLUMN pfa_human_binary_normal.file_name IS 'ファイル名';
COMMENT ON COLUMN pfa_human_binary_normal.file_remark IS 'ファイル備考';
COMMENT ON COLUMN pfa_human_binary_normal.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_human_binary_normal.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_binary_normal.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_binary_normal.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_binary_normal.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_binary_normal.update_user IS '更新者';


CREATE TABLE pfa_human_binary_history
(
  pfa_human_binary_history_id bigint NOT NULL DEFAULT 0, 
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  human_item_type character varying(50) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  human_item_binary bytea,
  file_type character varying(10) NOT NULL DEFAULT ''::character varying,
  file_name character varying(50) NOT NULL DEFAULT ''::character varying,
  file_remark text NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_binary_history_pkey PRIMARY KEY (pfa_human_binary_history_id)
)
;
COMMENT ON TABLE pfa_human_binary_history IS '人事汎用バイナリ履歴情報';
COMMENT ON COLUMN pfa_human_binary_history.pfa_human_binary_history_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_binary_history.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_binary_history.human_item_type IS '人事項目区分';
COMMENT ON COLUMN pfa_human_binary_history.activate_date IS '有効日';
COMMENT ON COLUMN pfa_human_binary_history.human_item_binary IS '人事項目バイナリ値';
COMMENT ON COLUMN pfa_human_binary_history.file_type IS 'ファイル拡張子';
COMMENT ON COLUMN pfa_human_binary_history.file_name IS 'ファイル名';
COMMENT ON COLUMN pfa_human_binary_history.file_remark IS 'ファイル備考';
COMMENT ON COLUMN pfa_human_binary_history.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_human_binary_history.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_binary_history.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_binary_history.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_binary_history.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_binary_history.update_user IS '更新者';


CREATE TABLE pfa_human_binary_array
(
  pfa_human_binary_array_id bigint NOT NULL DEFAULT 0, 
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  human_item_type character varying(50) NOT NULL DEFAULT ''::character varying,
  row_id integer NOT NULL DEFAULT 0,
  activate_date date NOT NULL,
  human_item_binary bytea,
  file_type character varying(10) NOT NULL DEFAULT ''::character varying,
  file_name character varying(50) NOT NULL DEFAULT ''::character varying,
  file_remark text NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_human_binary_array_pkey PRIMARY KEY (pfa_human_binary_array_id)
)
;
COMMENT ON TABLE pfa_human_binary_array IS '人事汎用バイナリ一覧情報';
COMMENT ON COLUMN pfa_human_binary_array.pfa_human_binary_array_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_human_binary_array.personal_id IS '個人ID';
COMMENT ON COLUMN pfa_human_binary_array.human_item_type IS '人事項目区分';
COMMENT ON COLUMN pfa_human_binary_array.row_id IS '行ID';
COMMENT ON COLUMN pfa_human_binary_array.activate_date IS '有効日';
COMMENT ON COLUMN pfa_human_binary_array.human_item_binary IS '人事項目バイナリ値';
COMMENT ON COLUMN pfa_human_binary_array.file_type IS 'ファイル拡張子';
COMMENT ON COLUMN pfa_human_binary_array.file_name IS 'ファイル名';
COMMENT ON COLUMN pfa_human_binary_array.file_remark IS 'ファイル備考';
COMMENT ON COLUMN pfa_human_binary_array.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_human_binary_array.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_human_binary_array.insert_date IS '登録日';
COMMENT ON COLUMN pfa_human_binary_array.insert_user IS '登録者';
COMMENT ON COLUMN pfa_human_binary_array.update_date IS '更新日';
COMMENT ON COLUMN pfa_human_binary_array.update_user IS '更新者';


CREATE TABLE pfg_general
(
  pfg_general_id bigint NOT NULL DEFAULT 0,
  general_type character varying(255) NOT NULL DEFAULT ''::character varying,
  general_code character varying(255) NOT NULL DEFAULT ''::character varying,
  general_date date,
  general_char1 character varying(255) DEFAULT ''::character varying,
  general_char2 character varying(255) DEFAULT ''::character varying,
  general_numeric double precision NOT NULL DEFAULT 0,
  general_time timestamp without time zone,
  general_binary bytea,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfg_general_pkey PRIMARY KEY (pfg_general_id)
)
;
COMMENT ON TABLE pfg_general IS 'MosP汎用テーブル';
COMMENT ON COLUMN pfg_general.pfg_general_id IS 'レコード識別ID';
COMMENT ON COLUMN pfg_general.general_type IS '汎用区分';
COMMENT ON COLUMN pfg_general.general_code IS '汎用コード';
COMMENT ON COLUMN pfg_general.general_date IS '汎用日付';
COMMENT ON COLUMN pfg_general.general_char1 IS '汎用文字列1';
COMMENT ON COLUMN pfg_general.general_char2 IS '汎用文字列2';
COMMENT ON COLUMN pfg_general.general_numeric IS '汎用数値';
COMMENT ON COLUMN pfg_general.general_time IS '汎用日時';
COMMENT ON COLUMN pfg_general.general_binary IS '汎用バイナリ';
COMMENT ON COLUMN pfg_general.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfg_general.insert_date IS '登録日';
COMMENT ON COLUMN pfg_general.insert_user IS '登録者';
COMMENT ON COLUMN pfg_general.update_date IS '更新日';
COMMENT ON COLUMN pfg_general.update_user IS '更新者';


CREATE TABLE pfm_employment_contract
(
  pfm_employment_contract_id bigint NOT NULL DEFAULT 0,
  employment_contract_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  employment_contract_name character varying(50) NOT NULL DEFAULT ''::character varying,
  employment_contract_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_employment_contract_pkey PRIMARY KEY (pfm_employment_contract_id)
)
;
COMMENT ON TABLE pfm_employment_contract IS '雇用契約マスタ';
COMMENT ON COLUMN pfm_employment_contract.pfm_employment_contract_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_employment_contract.employment_contract_code IS '雇用契約コード';
COMMENT ON COLUMN pfm_employment_contract.activate_date IS '有効日';
COMMENT ON COLUMN pfm_employment_contract.employment_contract_name IS '雇用契約名称';
COMMENT ON COLUMN pfm_employment_contract.employment_contract_abbr IS '雇用契約略称';
COMMENT ON COLUMN pfm_employment_contract.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_employment_contract.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_employment_contract.insert_date IS '登録日';
COMMENT ON COLUMN pfm_employment_contract.insert_user IS '登録者';
COMMENT ON COLUMN pfm_employment_contract.update_date IS '更新日';
COMMENT ON COLUMN pfm_employment_contract.update_user IS '更新者';


CREATE TABLE pfm_human
(
  pfm_human_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  employee_code character varying(10) NOT NULL DEFAULT ''::character varying,
  last_name character varying(50) NOT NULL DEFAULT ''::character varying,
  first_name character varying(50) NOT NULL DEFAULT ''::character varying,
  last_kana character varying(50) NOT NULL DEFAULT ''::character varying,
  first_kana character varying(50) NOT NULL DEFAULT ''::character varying,
  employment_contract_code character varying(10) NOT NULL DEFAULT ''::character varying,
  section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  position_code character varying(10) NOT NULL DEFAULT ''::character varying,
  work_place_code character varying(10) NOT NULL DEFAULT ''::character varying,
  mail character varying(50) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_human_pkey PRIMARY KEY (pfm_human_id)
)
;
COMMENT ON TABLE pfm_human IS '人事マスタ';
COMMENT ON COLUMN pfm_human.pfm_human_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_human.personal_id IS '個人ID';
COMMENT ON COLUMN pfm_human.activate_date IS '有効日';
COMMENT ON COLUMN pfm_human.employee_code IS '社員コード';
COMMENT ON COLUMN pfm_human.last_name IS '姓';
COMMENT ON COLUMN pfm_human.first_name IS '名';
COMMENT ON COLUMN pfm_human.last_kana IS 'カナ姓';
COMMENT ON COLUMN pfm_human.first_kana IS 'カナ名';
COMMENT ON COLUMN pfm_human.employment_contract_code IS '雇用契約コード';
COMMENT ON COLUMN pfm_human.section_code IS '所属コード';
COMMENT ON COLUMN pfm_human.position_code IS '職位コード';
COMMENT ON COLUMN pfm_human.work_place_code IS '勤務地コード';
COMMENT ON COLUMN pfm_human.mail IS 'メールアドレス';
COMMENT ON COLUMN pfm_human.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_human.insert_date IS '登録日';
COMMENT ON COLUMN pfm_human.insert_user IS '登録者';
COMMENT ON COLUMN pfm_human.update_date IS '更新日';
COMMENT ON COLUMN pfm_human.update_user IS '更新者';


CREATE TABLE pfm_position
(
  pfm_position_id bigint NOT NULL DEFAULT 0,
  position_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  position_name character varying(50) NOT NULL DEFAULT ''::character varying,
  position_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  position_grade integer NOT NULL DEFAULT 0,
  position_level integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_position_pkey PRIMARY KEY (pfm_position_id)
)
;
COMMENT ON TABLE pfm_position IS '職位マスタ';
COMMENT ON COLUMN pfm_position.pfm_position_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_position.position_code IS '職位コード';
COMMENT ON COLUMN pfm_position.activate_date IS '有効日';
COMMENT ON COLUMN pfm_position.position_name IS '職位名称';
COMMENT ON COLUMN pfm_position.position_abbr IS '職位略称';
COMMENT ON COLUMN pfm_position.position_grade IS '等級';
COMMENT ON COLUMN pfm_position.position_level IS '号数';
COMMENT ON COLUMN pfm_position.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_position.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_position.insert_date IS '登録日';
COMMENT ON COLUMN pfm_position.insert_user IS '登録者';
COMMENT ON COLUMN pfm_position.update_date IS '更新日';
COMMENT ON COLUMN pfm_position.update_user IS '更新者';


CREATE TABLE pfm_section
(
  pfm_section_id bigint NOT NULL DEFAULT 0,
  section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  section_name character varying(50) NOT NULL DEFAULT ''::character varying,
  section_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  section_display character varying(50) NOT NULL DEFAULT ''::character varying,
  class_route text NOT NULL DEFAULT ''::text,
  close_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_section_pkey PRIMARY KEY (pfm_section_id)
)
;
COMMENT ON TABLE pfm_section IS '所属マスタ';
COMMENT ON COLUMN pfm_section.pfm_section_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_section.section_code IS '所属コード';
COMMENT ON COLUMN pfm_section.activate_date IS '有効日';
COMMENT ON COLUMN pfm_section.section_name IS '所属名称';
COMMENT ON COLUMN pfm_section.section_abbr IS '所属略称';
COMMENT ON COLUMN pfm_section.section_display IS '所属表示名称';
COMMENT ON COLUMN pfm_section.class_route IS '階層経路';
COMMENT ON COLUMN pfm_section.close_flag IS '閉鎖フラグ';
COMMENT ON COLUMN pfm_section.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_section.insert_date IS '登録日';
COMMENT ON COLUMN pfm_section.insert_user IS '登録者';
COMMENT ON COLUMN pfm_section.update_date IS '更新日';
COMMENT ON COLUMN pfm_section.update_user IS '更新者';


CREATE TABLE pfm_user
(
  pfm_user_id bigint NOT NULL DEFAULT 0,
  user_id character varying(50) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  role_code character varying(50) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_user_pkey PRIMARY KEY (pfm_user_id)
)
;
COMMENT ON TABLE pfm_user IS 'ユーザマスタ';
COMMENT ON COLUMN pfm_user.pfm_user_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_user.user_id IS 'ユーザID';
COMMENT ON COLUMN pfm_user.activate_date IS '有効日';
COMMENT ON COLUMN pfm_user.personal_id IS '個人ID';
COMMENT ON COLUMN pfm_user.role_code IS 'ロールコード';
COMMENT ON COLUMN pfm_user.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_user.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_user.insert_date IS '登録日';
COMMENT ON COLUMN pfm_user.insert_user IS '登録者';
COMMENT ON COLUMN pfm_user.update_date IS '更新日';
COMMENT ON COLUMN pfm_user.update_user IS '更新者';


CREATE TABLE pfm_work_place
(
  pfm_work_place_id bigint NOT NULL DEFAULT 0,
  work_place_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  work_place_name character varying(50) NOT NULL DEFAULT ''::character varying,
  work_place_kana character varying(50) NOT NULL DEFAULT ''::character varying,
  work_place_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  prefecture character varying(10) NOT NULL DEFAULT ''::character varying,
  address_1 character varying(50) NOT NULL DEFAULT ''::character varying,
  address_2 character varying(50) NOT NULL DEFAULT ''::character varying,
  address_3 character varying(50) NOT NULL DEFAULT ''::character varying,
  postal_code_1 character varying(3) NOT NULL DEFAULT ''::character varying,
  postal_code_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  phone_number_1 character varying(5) NOT NULL DEFAULT ''::character varying,
  phone_number_2 character varying(4) NOT NULL DEFAULT ''::character varying,
  phone_number_3 character varying(4) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_work_place_pkey PRIMARY KEY (pfm_work_place_id)
)
;
COMMENT ON TABLE pfm_work_place IS '勤務地マスタ';
COMMENT ON COLUMN pfm_work_place.pfm_work_place_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_work_place.work_place_code IS '勤務地コード';
COMMENT ON COLUMN pfm_work_place.activate_date IS '有効日';
COMMENT ON COLUMN pfm_work_place.work_place_name IS '勤務地名称';
COMMENT ON COLUMN pfm_work_place.work_place_kana IS '勤務地カナ';
COMMENT ON COLUMN pfm_work_place.work_place_abbr IS '勤務地略称';
COMMENT ON COLUMN pfm_work_place.prefecture IS '勤務地都道府県';
COMMENT ON COLUMN pfm_work_place.address_1 IS '勤務地市区町村';
COMMENT ON COLUMN pfm_work_place.address_2 IS '勤務地番地';
COMMENT ON COLUMN pfm_work_place.address_3 IS '勤務地建物情報';
COMMENT ON COLUMN pfm_work_place.postal_code_1 IS '勤務地郵便番号1';
COMMENT ON COLUMN pfm_work_place.postal_code_2 IS '勤務地郵便番号2';
COMMENT ON COLUMN pfm_work_place.phone_number_1 IS '勤務地電話番号1';
COMMENT ON COLUMN pfm_work_place.phone_number_2 IS '勤務地電話番号2';
COMMENT ON COLUMN pfm_work_place.phone_number_3 IS '勤務地電話番号3';
COMMENT ON COLUMN pfm_work_place.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_work_place.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_work_place.insert_date IS '登録日';
COMMENT ON COLUMN pfm_work_place.insert_user IS '登録者';
COMMENT ON COLUMN pfm_work_place.update_date IS '更新日';
COMMENT ON COLUMN pfm_work_place.update_user IS '更新者';


CREATE TABLE pfm_naming
(
  pfm_naming_id bigint NOT NULL DEFAULT 0, 
  naming_type character varying(20) NOT NULL DEFAULT ''::character varying, 
  naming_item_code character varying(10) NOT NULL DEFAULT ''::character varying, 
  activate_date date NOT NULL, 
  naming_item_name character varying(50) NOT NULL DEFAULT ''::character varying, 
  naming_item_abbr character varying(6) NOT NULL DEFAULT ''::character varying, 
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0, 
  insert_date timestamp without time zone NOT NULL, 
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL, 
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_naming_pkey PRIMARY KEY (pfm_naming_id)
)
;
COMMENT ON TABLE pfm_naming IS '名称区分マスタ';
COMMENT ON COLUMN pfm_naming.pfm_naming_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_naming.naming_type IS '名称区分コード';
COMMENT ON COLUMN pfm_naming.naming_item_code IS '名称項目コード';
COMMENT ON COLUMN pfm_naming.activate_date IS '有効日';
COMMENT ON COLUMN pfm_naming.naming_item_name IS '名称項目名称';
COMMENT ON COLUMN pfm_naming.naming_item_abbr IS '名称項目略称';
COMMENT ON COLUMN pfm_naming.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_naming.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_naming.insert_date IS '登録日';
COMMENT ON COLUMN pfm_naming.insert_user IS '登録者';
COMMENT ON COLUMN pfm_naming.update_date IS '更新日';
COMMENT ON COLUMN pfm_naming.update_user IS '更新者';


CREATE TABLE pfm_ic_card
(
  pfm_ic_card_id bigint NOT NULL DEFAULT 0,
  ic_card_id character varying(50) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_ic_card_pkey PRIMARY KEY (pfm_ic_card_id)
)
;
COMMENT ON TABLE pfm_ic_card IS 'ICカードマスタ';
COMMENT ON COLUMN pfm_ic_card.pfm_ic_card_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_ic_card.ic_card_id IS 'ICカードID';
COMMENT ON COLUMN pfm_ic_card.activate_date IS '有効日';
COMMENT ON COLUMN pfm_ic_card.personal_id IS '個人ID';
COMMENT ON COLUMN pfm_ic_card.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_ic_card.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_ic_card.insert_date IS '登録日';
COMMENT ON COLUMN pfm_ic_card.insert_user IS '登録者';
COMMENT ON COLUMN pfm_ic_card.update_date IS '更新日';
COMMENT ON COLUMN pfm_ic_card.update_user IS '更新者';


CREATE TABLE pft_reception_ic_card
(
  pft_reception_ic_card_id bigint NOT NULL DEFAULT 0,
  ic_card_id character varying(50) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pft_reception_ic_card_pkey PRIMARY KEY (pft_reception_ic_card_id )
)
;
COMMENT ON TABLE pft_reception_ic_card IS 'ICカード受付テーブル';
COMMENT ON COLUMN pft_reception_ic_card.pft_reception_ic_card_id IS 'レコード識別ID';
COMMENT ON COLUMN pft_reception_ic_card.ic_card_id IS 'ICカードID';
COMMENT ON COLUMN pft_reception_ic_card.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pft_reception_ic_card.insert_date IS '登録日';
COMMENT ON COLUMN pft_reception_ic_card.insert_user IS '登録者';
COMMENT ON COLUMN pft_reception_ic_card.update_date IS '更新日';
COMMENT ON COLUMN pft_reception_ic_card.update_user IS '更新者';


CREATE TABLE pft_workflow
(
  pft_workflow_id bigint NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  workflow_stage integer NOT NULL DEFAULT 0,
  workflow_status character varying(10) NOT NULL DEFAULT ''::character varying,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  workflow_date date NOT NULL,
  route_code character varying(10) NOT NULL DEFAULT ''::character varying,
  function_code character varying(10) NOT NULL DEFAULT ''::character varying,
  approver_id text NOT NULL DEFAULT ''::text,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pft_workflow_pkey PRIMARY KEY (pft_workflow_id)
)
;
COMMENT ON TABLE pft_workflow IS 'ワークフロー';
COMMENT ON COLUMN pft_workflow.pft_workflow_id IS 'レコード識別ID';
COMMENT ON COLUMN pft_workflow.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN pft_workflow.workflow_stage IS '段階';
COMMENT ON COLUMN pft_workflow.workflow_status IS '状況';
COMMENT ON COLUMN pft_workflow.personal_id IS '申請者個人ID';
COMMENT ON COLUMN pft_workflow.workflow_date IS 'ワークフロー対象日';
COMMENT ON COLUMN pft_workflow.route_code IS 'ルートコード';
COMMENT ON COLUMN pft_workflow.function_code IS '機能コード';
COMMENT ON COLUMN pft_workflow.approver_id IS '承認者個人ID';
COMMENT ON COLUMN pft_workflow.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pft_workflow.insert_date IS '登録日';
COMMENT ON COLUMN pft_workflow.insert_user IS '登録者';
COMMENT ON COLUMN pft_workflow.update_date IS '更新日';
COMMENT ON COLUMN pft_workflow.update_user IS '更新者';


CREATE TABLE pft_workflow_comment
(
  pft_workflow_comment_id bigint NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  workflow_stage integer NOT NULL DEFAULT 0,
  workflow_status character varying(10) NOT NULL DEFAULT ''::character varying,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  workflow_comment character varying(50) NOT NULL DEFAULT ''::character varying,
  workflow_date timestamp without time zone NOT NULL,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pft_workflow_comment_pkey PRIMARY KEY (pft_workflow_comment_id)
)
;
COMMENT ON TABLE pft_workflow_comment IS 'ワークフローコメント';
COMMENT ON COLUMN pft_workflow_comment.pft_workflow_comment_id IS 'レコード識別ID';
COMMENT ON COLUMN pft_workflow_comment.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN pft_workflow_comment.workflow_stage IS '段階';
COMMENT ON COLUMN pft_workflow_comment.workflow_status IS '状況';
COMMENT ON COLUMN pft_workflow_comment.personal_id IS '個人ID';
COMMENT ON COLUMN pft_workflow_comment.workflow_comment IS 'コメント';
COMMENT ON COLUMN pft_workflow_comment.workflow_date IS '日時';
COMMENT ON COLUMN pft_workflow_comment.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pft_workflow_comment.insert_date IS '登録日';
COMMENT ON COLUMN pft_workflow_comment.insert_user IS '登録者';
COMMENT ON COLUMN pft_workflow_comment.update_date IS '更新日';
COMMENT ON COLUMN pft_workflow_comment.update_user IS '更新者';


CREATE TABLE pfm_approval_unit
(
  pfm_approval_unit_id bigint NOT NULL DEFAULT 0,
  unit_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  unit_name character varying(50) NOT NULL DEFAULT ''::character varying,
  unit_type character varying(10) NOT NULL DEFAULT ''::character varying,
  approver_personal_id text NOT NULL DEFAULT ''::character varying,
  approver_section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  approver_position_code character varying(10) NOT NULL DEFAULT ''::character varying,
  approver_position_grade character varying(10) NOT NULL DEFAULT ''::character varying,
  route_stage integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_approval_unit_pkey PRIMARY KEY (pfm_approval_unit_id)
)
;
COMMENT ON TABLE pfm_approval_unit IS '承認ユニットマスタ';
COMMENT ON COLUMN pfm_approval_unit.pfm_approval_unit_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_approval_unit.unit_code IS 'ユニットコード';
COMMENT ON COLUMN pfm_approval_unit.activate_date IS '有効日';
COMMENT ON COLUMN pfm_approval_unit.unit_name IS 'ユニット名称';
COMMENT ON COLUMN pfm_approval_unit.unit_type IS 'ユニット区分';
COMMENT ON COLUMN pfm_approval_unit.approver_personal_id IS '承認者個人ID';
COMMENT ON COLUMN pfm_approval_unit.approver_section_code IS '承認者所属コード';
COMMENT ON COLUMN pfm_approval_unit.approver_position_code IS '承認者職位コード';
COMMENT ON COLUMN pfm_approval_unit.approver_position_grade IS '承認者職位等級範囲';
COMMENT ON COLUMN pfm_approval_unit.route_stage IS '複数決済';
COMMENT ON COLUMN pfm_approval_unit.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_approval_unit.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_approval_unit.insert_date IS '登録日';
COMMENT ON COLUMN pfm_approval_unit.insert_user IS '登録者';
COMMENT ON COLUMN pfm_approval_unit.update_date IS '更新日';
COMMENT ON COLUMN pfm_approval_unit.update_user IS '更新者';


CREATE TABLE pfm_approval_route
(
  pfm_approval_route_id bigint NOT NULL DEFAULT 0,
  route_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  route_name character varying(50) NOT NULL DEFAULT ''::character varying,
  approval_count integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_approval_route_pkey PRIMARY KEY (pfm_approval_route_id)
)
;
COMMENT ON TABLE pfm_approval_route IS '承認ルートマスタ';
COMMENT ON COLUMN pfm_approval_route.pfm_approval_route_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_approval_route.route_code IS 'ルートコード';
COMMENT ON COLUMN pfm_approval_route.activate_date IS '有効日';
COMMENT ON COLUMN pfm_approval_route.route_name IS 'ルート名称';
COMMENT ON COLUMN pfm_approval_route.approval_count IS '承認階層';
COMMENT ON COLUMN pfm_approval_route.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_approval_route.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_approval_route.insert_date IS '登録日';
COMMENT ON COLUMN pfm_approval_route.insert_user IS '登録者';
COMMENT ON COLUMN pfm_approval_route.update_date IS '更新日';
COMMENT ON COLUMN pfm_approval_route.update_user IS '更新者';


CREATE TABLE pfa_approval_route_unit
(
  pfa_approval_route_unit_id bigint NOT NULL DEFAULT 0,
  route_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  approval_stage integer NOT NULL DEFAULT 0,
  unit_code character varying(10) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_approval_route_unit_pkey PRIMARY KEY (pfa_approval_route_unit_id)
)
;
COMMENT ON TABLE pfa_approval_route_unit IS '承認ルートユニットマスタ';
COMMENT ON COLUMN pfa_approval_route_unit.pfa_approval_route_unit_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_approval_route_unit.route_code IS 'ルートコード';
COMMENT ON COLUMN pfa_approval_route_unit.activate_date IS '有効日';
COMMENT ON COLUMN pfa_approval_route_unit.approval_stage IS '承認段階';
COMMENT ON COLUMN pfa_approval_route_unit.unit_code IS 'ユニットコード';
COMMENT ON COLUMN pfa_approval_route_unit.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_approval_route_unit.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_approval_route_unit.insert_date IS '登録日';
COMMENT ON COLUMN pfa_approval_route_unit.insert_user IS '登録者';
COMMENT ON COLUMN pfa_approval_route_unit.update_date IS '更新日';
COMMENT ON COLUMN pfa_approval_route_unit.update_user IS '更新者';


CREATE TABLE pft_message
(
  pft_message_id bigint NOT NULL DEFAULT 0,
  message_no character varying(10) NOT NULL DEFAULT ''::character varying,
  start_date date NOT NULL,
  end_date date NOT NULL,
  message_type integer NOT NULL DEFAULT 0,
  message_importance integer NOT NULL DEFAULT 0,
  message_title character varying(255) NOT NULL DEFAULT ''::character varying,
  message_body text NOT NULL DEFAULT ''::character varying,
  application_type integer NOT NULL DEFAULT 0,
  work_place_code character varying(10) NOT NULL DEFAULT ''::character varying,
  employment_contract_code character varying(10) NOT NULL DEFAULT ''::character varying,
  section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  position_code character varying(10) NOT NULL DEFAULT ''::character varying,
  personal_ids text NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pft_message_pkey PRIMARY KEY (pft_message_id)
)
;
COMMENT ON TABLE pft_message IS 'メッセージテーブル';
COMMENT ON COLUMN pft_message.pft_message_id IS 'レコード識別ID';
COMMENT ON COLUMN pft_message.message_no IS 'メッセージNo';
COMMENT ON COLUMN pft_message.start_date IS '公開開始日';
COMMENT ON COLUMN pft_message.end_date IS '公開終了日';
COMMENT ON COLUMN pft_message.message_type IS 'メッセージ区分';
COMMENT ON COLUMN pft_message.message_importance IS '重要度';
COMMENT ON COLUMN pft_message.message_title IS 'メッセージタイトル';
COMMENT ON COLUMN pft_message.message_body IS 'メッセージ本文';
COMMENT ON COLUMN pft_message.application_type IS 'メッセージ適用範囲区分';
COMMENT ON COLUMN pft_message.work_place_code IS '勤務地コード';
COMMENT ON COLUMN pft_message.employment_contract_code IS '雇用契約コード';
COMMENT ON COLUMN pft_message.section_code IS '所属コード';
COMMENT ON COLUMN pft_message.position_code IS '職位コード';
COMMENT ON COLUMN pft_message.personal_ids IS '個人ID';
COMMENT ON COLUMN pft_message.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pft_message.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pft_message.insert_date IS '登録日';
COMMENT ON COLUMN pft_message.insert_user IS '登録者';
COMMENT ON COLUMN pft_message.update_date IS '更新日';
COMMENT ON COLUMN pft_message.update_user IS '更新者';


CREATE TABLE pft_sub_approver
(
  pft_sub_approver_id bigint NOT NULL DEFAULT 0,
  sub_approver_no character varying(10) NOT NULL DEFAULT ''::character varying,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  workflow_type integer NOT NULL DEFAULT 0,
  start_date date NOT NULL,
  end_date date NOT NULL,
  sub_approver_id character varying(10) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pft_sub_approver_pkey PRIMARY KEY (pft_sub_approver_id)
)
;
COMMENT ON TABLE pft_sub_approver IS '代理承認者テーブル';
COMMENT ON COLUMN pft_sub_approver.pft_sub_approver_id IS 'レコード識別ID';
COMMENT ON COLUMN pft_sub_approver.sub_approver_no IS '代理承認者登録No';
COMMENT ON COLUMN pft_sub_approver.personal_id IS '代理元個人ID';
COMMENT ON COLUMN pft_sub_approver.workflow_type IS 'フロー区分';
COMMENT ON COLUMN pft_sub_approver.start_date IS '代理開始日';
COMMENT ON COLUMN pft_sub_approver.end_date IS '代理終了日';
COMMENT ON COLUMN pft_sub_approver.sub_approver_id IS '代理承認者個人ID';
COMMENT ON COLUMN pft_sub_approver.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pft_sub_approver.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pft_sub_approver.insert_date IS '登録日';
COMMENT ON COLUMN pft_sub_approver.insert_user IS '登録者';
COMMENT ON COLUMN pft_sub_approver.update_date IS '更新日';
COMMENT ON COLUMN pft_sub_approver.update_user IS '更新者';


CREATE TABLE pfm_route_application
(
  pfm_route_application_id bigint NOT NULL DEFAULT 0,
  route_application_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  route_application_name character varying(50) NOT NULL DEFAULT ''::character varying,
  route_code character varying(10) NOT NULL DEFAULT ''::character varying,
  workflow_type integer NOT NULL DEFAULT 0,
  route_application_type integer NOT NULL DEFAULT 0,
  work_place_code character varying(10) NOT NULL DEFAULT ''::character varying,
  employment_contract_code character varying(10) NOT NULL DEFAULT ''::character varying,
  section_code character varying(10) NOT NULL DEFAULT ''::character varying,
  position_code character varying(10) NOT NULL DEFAULT ''::character varying,
  personal_ids text NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_route_application_pkey PRIMARY KEY (pfm_route_application_id)
)
;
COMMENT ON TABLE pfm_route_application IS 'ルート適用マスタ';
COMMENT ON COLUMN pfm_route_application.pfm_route_application_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_route_application.route_application_code IS 'ルート適用コード';
COMMENT ON COLUMN pfm_route_application.activate_date IS '有効日';
COMMENT ON COLUMN pfm_route_application.route_application_name IS 'ルート適用名称';
COMMENT ON COLUMN pfm_route_application.route_code IS 'ルートコード';
COMMENT ON COLUMN pfm_route_application.workflow_type IS 'フロー区分';
COMMENT ON COLUMN pfm_route_application.route_application_type IS 'ルート適用区分';
COMMENT ON COLUMN pfm_route_application.work_place_code IS '勤務地コード';
COMMENT ON COLUMN pfm_route_application.employment_contract_code IS '雇用契約コード';
COMMENT ON COLUMN pfm_route_application.section_code IS '所属コード';
COMMENT ON COLUMN pfm_route_application.position_code IS '職位コード';
COMMENT ON COLUMN pfm_route_application.personal_ids IS '個人ID';
COMMENT ON COLUMN pfm_route_application.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_route_application.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_route_application.insert_date IS '登録日';
COMMENT ON COLUMN pfm_route_application.insert_user IS '登録者';
COMMENT ON COLUMN pfm_route_application.update_date IS '更新日';
COMMENT ON COLUMN pfm_route_application.update_user IS '更新者';


CREATE TABLE pfm_import
(
  pfm_import_id bigint NOT NULL DEFAULT 0,
  import_code character varying(10) NOT NULL DEFAULT ''::character varying,
  import_name character varying(50) NOT NULL DEFAULT ''::character varying,
  import_table character varying(50) NOT NULL DEFAULT ''::character varying,
  type character varying(50) NOT NULL DEFAULT ''::character varying,
  header integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_import_pkey PRIMARY KEY (pfm_import_id)
)
;
COMMENT ON TABLE pfm_import IS 'インポートマスタ';
COMMENT ON COLUMN pfm_import.pfm_import_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_import.import_code IS 'インポートコード';
COMMENT ON COLUMN pfm_import.import_name IS 'インポート名称';
COMMENT ON COLUMN pfm_import.import_table IS 'データ区分';
COMMENT ON COLUMN pfm_import.type IS 'データ型';
COMMENT ON COLUMN pfm_import.header IS 'ヘッダ有無';
COMMENT ON COLUMN pfm_import.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_import.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_import.insert_date IS '登録日';
COMMENT ON COLUMN pfm_import.insert_user IS '登録者';
COMMENT ON COLUMN pfm_import.update_date IS '更新日';
COMMENT ON COLUMN pfm_import.update_user IS '更新者';


CREATE TABLE pfa_import_field
(
  pfa_import_field_id bigint NOT NULL DEFAULT 0,
  import_code character varying(10) NOT NULL DEFAULT ''::character varying,
  field_name character varying(64) NOT NULL DEFAULT ''::character varying,
  field_order integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_import_field_pkey PRIMARY KEY (pfa_import_field_id)
)
;
COMMENT ON TABLE pfa_import_field IS 'インポートフィールド情報';
COMMENT ON COLUMN pfa_import_field.pfa_import_field_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_import_field.import_code IS 'インポートコード';
COMMENT ON COLUMN pfa_import_field.field_name IS 'フィールド名称';
COMMENT ON COLUMN pfa_import_field.field_order IS 'フィールド順序';
COMMENT ON COLUMN pfa_import_field.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_import_field.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_import_field.insert_date IS '登録日';
COMMENT ON COLUMN pfa_import_field.insert_user IS '登録者';
COMMENT ON COLUMN pfa_import_field.update_date IS '更新日';
COMMENT ON COLUMN pfa_import_field.update_user IS '更新者';


CREATE TABLE pfm_export
(
  pfm_export_id bigint NOT NULL DEFAULT 0,
  export_code character varying(10) NOT NULL DEFAULT ''::character varying,
  export_name character varying(50) NOT NULL DEFAULT ''::character varying,
  export_table character varying(50) NOT NULL DEFAULT ''::character varying,
  type character varying(50) NOT NULL DEFAULT ''::character varying,
  header integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_export_pkey PRIMARY KEY (pfm_export_id)
)
;
COMMENT ON TABLE pfm_export IS 'エクスポートマスタ';
COMMENT ON COLUMN pfm_export.pfm_export_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_export.export_code IS 'エクスポートコード';
COMMENT ON COLUMN pfm_export.export_name IS 'エクスポート名称';
COMMENT ON COLUMN pfm_export.export_table IS 'データ区分';
COMMENT ON COLUMN pfm_export.type IS 'データ型';
COMMENT ON COLUMN pfm_export.header IS 'ヘッダ有無';
COMMENT ON COLUMN pfm_export.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfm_export.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_export.insert_date IS '登録日';
COMMENT ON COLUMN pfm_export.insert_user IS '登録者';
COMMENT ON COLUMN pfm_export.update_date IS '更新日';
COMMENT ON COLUMN pfm_export.update_user IS '更新者';


CREATE TABLE pfa_export_field
(
  pfa_export_field_id bigint NOT NULL DEFAULT 0,
  export_code character varying(10) NOT NULL DEFAULT ''::character varying,
  field_name character varying(64) NOT NULL DEFAULT ''::character varying,
  field_order integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfa_export_field_pkey PRIMARY KEY (pfa_export_field_id)
)
;
COMMENT ON TABLE pfa_export_field IS 'エクスポートフィールド情報';
COMMENT ON COLUMN pfa_export_field.pfa_export_field_id IS 'レコード識別ID';
COMMENT ON COLUMN pfa_export_field.export_code IS 'エクスポートコード';
COMMENT ON COLUMN pfa_export_field.field_name IS 'フィールド名称';
COMMENT ON COLUMN pfa_export_field.field_order IS 'フィールド順序';
COMMENT ON COLUMN pfa_export_field.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN pfa_export_field.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfa_export_field.insert_date IS '登録日';
COMMENT ON COLUMN pfa_export_field.insert_user IS '登録者';
COMMENT ON COLUMN pfa_export_field.update_date IS '更新日';
COMMENT ON COLUMN pfa_export_field.update_user IS '更新者';


CREATE TABLE pfm_app_property
(
  pfm_app_property_id bigint NOT NULL DEFAULT 0,
  app_key text NOT NULL DEFAULT ''::character varying,
  app_value text DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT pfm_app_property_pkey PRIMARY KEY (pfm_app_property_id)
)
;
COMMENT ON TABLE pfm_app_property IS 'アプリケーション設定マスタ';
COMMENT ON COLUMN pfm_app_property.pfm_app_property_id IS 'レコード識別ID';
COMMENT ON COLUMN pfm_app_property.app_key IS 'アプリケーション設定キー';
COMMENT ON COLUMN pfm_app_property.app_value IS 'アプリケーション設定値';
COMMENT ON COLUMN pfm_app_property.delete_flag IS '削除フラグ';
COMMENT ON COLUMN pfm_app_property.insert_date IS '登録日';
COMMENT ON COLUMN pfm_app_property.insert_user IS '登録者';
COMMENT ON COLUMN pfm_app_property.update_date IS '更新日';
COMMENT ON COLUMN pfm_app_property.update_user IS '更新者';


CREATE SEQUENCE pfm_postal_code_id_seq;
CREATE SEQUENCE pfm_bank_base_id_seq;
CREATE SEQUENCE pfm_bank_branch_id_seq;
CREATE SEQUENCE pfa_account_id_seq;
CREATE SEQUENCE pfa_address_id_seq;
CREATE SEQUENCE pfa_human_concurrent_id_seq;
CREATE SEQUENCE pfa_human_entrance_id_seq; 
CREATE SEQUENCE pfa_human_retirement_id_seq;
CREATE SEQUENCE pfa_human_suspension_id_seq;
CREATE SEQUENCE pfa_user_extra_role_id_seq;
CREATE SEQUENCE pfa_user_password_id_seq;
CREATE SEQUENCE pfa_human_normal_id_seq;
CREATE SEQUENCE pfa_human_history_id_seq;
CREATE SEQUENCE pfa_human_array_id_seq;
CREATE SEQUENCE pfa_human_binary_normal_id_seq;
CREATE SEQUENCE pfa_human_binary_history_id_seq;
CREATE SEQUENCE pfa_human_binary_array_id_seq;
CREATE SEQUENCE pfa_phone_id_seq;
CREATE SEQUENCE pfg_general_id_seq;
CREATE SEQUENCE pfm_employment_contract_id_seq;
CREATE SEQUENCE pfm_human_id_seq;
CREATE SEQUENCE pfm_position_id_seq;
CREATE SEQUENCE pfm_section_id_seq;
CREATE SEQUENCE pfm_user_id_seq;
CREATE SEQUENCE pfm_work_place_id_seq;
CREATE SEQUENCE pfm_naming_id_seq;
CREATE SEQUENCE pfm_ic_card_id_seq;
CREATE SEQUENCE pft_reception_ic_card_id_seq;
CREATE SEQUENCE pft_workflow_id_seq;
CREATE SEQUENCE pft_workflow_comment_id_seq;
CREATE SEQUENCE pfm_approval_unit_id_seq;
CREATE SEQUENCE pfm_approval_route_id_seq;
CREATE SEQUENCE pfa_approval_route_unit_id_seq;
CREATE SEQUENCE pft_message_id_seq;
CREATE SEQUENCE pft_sub_approver_id_seq;
CREATE SEQUENCE pfm_route_application_id_seq;
CREATE SEQUENCE pfm_import_id_seq;
CREATE SEQUENCE pfa_import_field_id_seq;
CREATE SEQUENCE pfm_export_id_seq;
CREATE SEQUENCE pfa_export_field_id_seq;
CREATE SEQUENCE pfm_human_personal_id_seq;
CREATE SEQUENCE pft_message_message_no_seq;
CREATE SEQUENCE pft_sub_approver_sub_approver_no_seq;
CREATE SEQUENCE pft_workflow_workflow_seq;
CREATE SEQUENCE pfm_app_property_id_seq;


CREATE INDEX pfm_postal_code_index1 ON pfm_postal_code(postal_code);
CREATE INDEX pfm_bank_base_index1 ON pfm_bank_base(bank_code);
CREATE INDEX pfm_bank_branch_index1 ON pfm_bank_branch(bank_code);
CREATE INDEX pfm_bank_branch_index2 ON pfm_bank_branch(branch_code);
CREATE INDEX pfa_account_index1 ON pfa_account(holder_id, account_type, activate_date);
CREATE INDEX pfa_account_index2 ON pfa_account(workflow);
CREATE INDEX pfa_address_index1 ON pfa_address(holder_id, activate_date, address_type);
CREATE INDEX pfa_approval_route_unit_index1 ON pfa_approval_route_unit(route_code, activate_date);
CREATE INDEX pfa_export_field_index1 ON pfa_export_field(export_code);
CREATE INDEX pfa_human_array_index1 ON pfa_human_array(personal_id, human_item_type);
CREATE INDEX pfa_human_binary_array_index1 ON pfa_human_binary_array(personal_id, human_item_type);
CREATE INDEX pfa_human_binary_history_index1 ON pfa_human_binary_history(personal_id, human_item_type, activate_date);
CREATE INDEX pfa_human_binary_normal_index1 ON pfa_human_binary_normal(personal_id, human_item_type);
CREATE INDEX pfa_human_concurrent_index1 ON pfa_human_concurrent(personal_id, start_date, end_date);
CREATE INDEX pfa_human_entrance_index1 ON pfa_human_entrance(personal_id, entrance_date);
CREATE INDEX pfa_human_history_index1 ON pfa_human_history(personal_id, human_item_type, activate_date);
CREATE INDEX pfa_human_normal_index1 ON pfa_human_normal(personal_id, human_item_type);
CREATE INDEX pfa_human_retirement_index1 ON pfa_human_retirement(personal_id, retirement_date);
CREATE INDEX pfa_human_suspension_index1 ON pfa_human_suspension(personal_id, start_date, end_date);
CREATE INDEX pfa_import_field_index1 ON pfa_import_field(import_code);
CREATE INDEX pfa_phone_index1 ON pfa_phone(holder_id, activate_date, phone_type);
CREATE INDEX pfa_user_extra_role_index1 ON pfa_user_extra_role(delete_flag, user_id, activate_date);
CREATE INDEX pfa_user_password_index1 ON pfa_user_password(user_id);
CREATE INDEX pfg_general_index1 ON pfg_general(general_type);
CREATE INDEX pfm_approval_route_index1 ON pfm_approval_route(route_code, activate_date);
CREATE INDEX pfm_approval_unit_index1 ON pfm_approval_unit(unit_code, activate_date);
CREATE INDEX pfm_employment_contract_index1 ON pfm_employment_contract(employment_contract_code, activate_date);
CREATE INDEX pfm_export_index1 ON pfm_export(export_code);
CREATE INDEX pfm_human_index1 ON pfm_human(personal_id, activate_date);
CREATE INDEX pfm_ic_card_index1 ON pfm_ic_card(ic_card_id, activate_date);
CREATE INDEX pfm_import_index1 ON pfm_import(import_code);
CREATE INDEX pfm_naming_index1 ON pfm_naming(naming_type, naming_item_code, activate_date);
CREATE INDEX pfm_position_index1 ON pfm_position(position_code, activate_date);
CREATE INDEX pfm_route_application_index1 ON pfm_route_application(route_application_code, activate_date);
CREATE INDEX pfm_section_index1 ON pfm_section(section_code, activate_date);
CREATE INDEX pfm_user_index1 ON pfm_user(user_id, activate_date);
CREATE INDEX pfm_work_place_index1 ON pfm_work_place(work_place_code, activate_date);
CREATE INDEX pft_message_index1 ON pft_message(message_no, start_date, end_date);
CREATE INDEX pft_sub_approver_index1 ON pft_sub_approver(start_date, end_date, sub_approver_id);
CREATE INDEX pft_workflow_index1 ON pft_workflow(workflow);
CREATE INDEX pft_workflow_index2 ON pft_workflow(workflow_status);
CREATE INDEX pft_workflow_index3 ON pft_workflow(personal_id);
CREATE INDEX pft_workflow_comment_index1 ON pft_workflow_comment(workflow);
CREATE INDEX pfm_app_property_index1 ON pfm_app_property(delete_flag, app_key);


