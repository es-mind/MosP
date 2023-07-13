CREATE TABLE tmd_attendance
(
  tmd_attendance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  work_type_code character varying(20) NOT NULL DEFAULT ''::character varying,
  direct_start integer NOT NULL DEFAULT 0,
  direct_end integer NOT NULL DEFAULT 0,
  forgot_record_work_start integer NOT NULL DEFAULT 0,
  not_record_work_start integer NOT NULL DEFAULT 0,
  start_time timestamp without time zone,
  actual_start_time timestamp without time zone,
  end_time timestamp without time zone,
  actual_end_time timestamp without time zone,
  late_days integer NOT NULL DEFAULT 0,
  late_thirty_minutes_or_more integer NOT NULL DEFAULT 0,
  late_less_than_thirty_minutes integer NOT NULL DEFAULT 0,
  late_time integer NOT NULL DEFAULT 0,
  actual_late_time integer NOT NULL DEFAULT 0,
  late_thirty_minutes_or_more_time integer NOT NULL DEFAULT 0,
  late_less_than_thirty_minutes_time integer NOT NULL DEFAULT 0,
  late_reason character varying(10) NOT NULL DEFAULT ''::character varying,
  late_certificate character varying(10) NOT NULL DEFAULT ''::character varying,
  late_comment character varying(50) NOT NULL DEFAULT ''::character varying,
  leave_early_days integer NOT NULL DEFAULT 0,
  leave_early_thirty_minutes_or_more integer NOT NULL DEFAULT 0,
  leave_early_less_than_thirty_minutes integer NOT NULL DEFAULT 0,
  leave_early_time integer NOT NULL DEFAULT 0,
  actual_leave_early_time integer NOT NULL DEFAULT 0,
  leave_early_thirty_minutes_or_more_time integer NOT NULL DEFAULT 0,
  leave_early_less_than_thirty_minutes_time integer NOT NULL DEFAULT 0,
  leave_early_reason character varying(10) NOT NULL DEFAULT ''::character varying,
  leave_early_certificate character varying(10) NOT NULL DEFAULT ''::character varying,
  leave_early_comment character varying(50) NOT NULL DEFAULT ''::character varying,
  work_time integer NOT NULL DEFAULT 0,
  general_work_time integer NOT NULL DEFAULT 0,
  work_time_within_prescribed_work_time integer NOT NULL DEFAULT 0,
  contract_work_time integer NOT NULL DEFAULT 0,
  short_unpaid integer NOT NULL DEFAULT 0,
  rest_time integer NOT NULL DEFAULT 0,
  over_rest_time integer NOT NULL DEFAULT 0,
  night_rest_time integer NOT NULL DEFAULT 0,
  legal_holiday_rest_time integer NOT NULL DEFAULT 0,
  prescribed_holiday_rest_time integer NOT NULL DEFAULT 0,
  public_time integer NOT NULL DEFAULT 0,
  private_time integer NOT NULL DEFAULT 0,
  minutely_holiday_a_time integer NOT NULL DEFAULT 0,
  minutely_holiday_b_time integer NOT NULL DEFAULT 0,
  minutely_holiday_a integer NOT NULL DEFAULT 0,
  minutely_holiday_b integer NOT NULL DEFAULT 0,
  times_overtime integer NOT NULL DEFAULT 0,
  overtime integer NOT NULL DEFAULT 0,
  overtime_before integer NOT NULL DEFAULT 0,
  overtime_after integer NOT NULL DEFAULT 0,
  overtime_in integer NOT NULL DEFAULT 0,
  overtime_out integer NOT NULL DEFAULT 0,
  workday_overtime_in integer NOT NULL DEFAULT 0,
  workday_overtime_out integer NOT NULL DEFAULT 0,
  prescribed_holiday_overtime_in integer NOT NULL DEFAULT 0,
  prescribed_holiday_overtime_out integer NOT NULL DEFAULT 0,
  late_night_time integer NOT NULL DEFAULT 0,
  night_work_within_prescribed_work integer NOT NULL DEFAULT 0,
  night_overtime_work integer NOT NULL DEFAULT 0,
  night_work_on_holiday integer NOT NULL DEFAULT 0,
  specific_work_time integer NOT NULL DEFAULT 0,
  legal_work_time integer NOT NULL DEFAULT 0,
  decrease_time integer NOT NULL DEFAULT 0,
  time_comment text NOT NULL DEFAULT ''::character varying,
  remarks text NOT NULL DEFAULT ''::character varying,
  work_days double precision NOT NULL DEFAULT 0,
  work_days_for_paid_leave integer NOT NULL DEFAULT 0,
  total_work_days_for_paid_leave integer NOT NULL DEFAULT 0,
  times_holiday_work integer NOT NULL DEFAULT 0,
  times_legal_holiday_work integer NOT NULL DEFAULT 0,
  times_prescribed_holiday_work integer NOT NULL DEFAULT 0,
  paid_leave_days double precision NOT NULL DEFAULT 0,
  paid_leave_hours integer NOT NULL DEFAULT 0,
  stock_leave_days double precision NOT NULL DEFAULT 0,
  compensation_days double precision NOT NULL DEFAULT 0,
  legal_compensation_days double precision NOT NULL DEFAULT 0,
  prescribed_compensation_days double precision NOT NULL DEFAULT 0,
  night_compensation_days double precision NOT NULL DEFAULT 0,
  special_leave_days double precision NOT NULL DEFAULT 0,
  special_leave_hours integer NOT NULL DEFAULT 0,
  other_leave_days double precision NOT NULL DEFAULT 0,
  other_leave_hours integer NOT NULL DEFAULT 0,
  absence_days double precision NOT NULL DEFAULT 0,
  absence_hours integer NOT NULL DEFAULT 0,
  granted_legal_compensation_days double precision NOT NULL DEFAULT 0,
  granted_prescribed_compensation_days double precision NOT NULL DEFAULT 0,
  granted_night_compensation_days double precision NOT NULL DEFAULT 0,
  legal_holiday_work_time_with_compensation_day integer NOT NULL DEFAULT 0,
  legal_holiday_work_time_without_compensation_day integer NOT NULL DEFAULT 0,
  prescribed_holiday_work_time_with_compensation_day integer NOT NULL DEFAULT 0,
  prescribed_holiday_work_time_without_compensation_day integer NOT NULL DEFAULT 0,
  overtime_in_with_compensation_day integer NOT NULL DEFAULT 0,
  overtime_in_without_compensation_day integer NOT NULL DEFAULT 0,
  overtime_out_with_compensation_day integer NOT NULL DEFAULT 0,
  overtime_out_without_compensation_day integer NOT NULL DEFAULT 0,
  statutory_holiday_work_time_in integer NOT NULL DEFAULT 0,
  statutory_holiday_work_time_out integer NOT NULL DEFAULT 0,
  prescribed_holiday_work_time_in integer NOT NULL DEFAULT 0,
  prescribed_holiday_work_time_out integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_attendance_pkey PRIMARY KEY (tmd_attendance_id)
)
;
COMMENT ON TABLE tmd_attendance IS '勤怠データ';
COMMENT ON COLUMN tmd_attendance.tmd_attendance_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_attendance.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_attendance.work_date IS '勤務日';
COMMENT ON COLUMN tmd_attendance.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_attendance.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmd_attendance.direct_start IS '直行';
COMMENT ON COLUMN tmd_attendance.direct_end IS '直帰';
COMMENT ON COLUMN tmd_attendance.forgot_record_work_start IS '始業忘れ';
COMMENT ON COLUMN tmd_attendance.not_record_work_start IS 'その他の始業できなかった場合';
COMMENT ON COLUMN tmd_attendance.start_time IS '始業時刻';
COMMENT ON COLUMN tmd_attendance.actual_start_time IS '実始業時刻';
COMMENT ON COLUMN tmd_attendance.end_time IS '終業時刻';
COMMENT ON COLUMN tmd_attendance.actual_end_time IS '実終業時刻';
COMMENT ON COLUMN tmd_attendance.late_days IS '遅刻日数';
COMMENT ON COLUMN tmd_attendance.late_thirty_minutes_or_more IS '遅刻30分以上日数';
COMMENT ON COLUMN tmd_attendance.late_less_than_thirty_minutes IS '遅刻30分未満日数';
COMMENT ON COLUMN tmd_attendance.late_time IS '遅刻時間';
COMMENT ON COLUMN tmd_attendance.actual_late_time IS '実遅刻時間';
COMMENT ON COLUMN tmd_attendance.late_thirty_minutes_or_more_time IS '遅刻30分以上時間';
COMMENT ON COLUMN tmd_attendance.late_less_than_thirty_minutes_time IS '遅刻30分未満時間';
COMMENT ON COLUMN tmd_attendance.late_reason IS '遅刻理由';
COMMENT ON COLUMN tmd_attendance.late_certificate IS '遅刻証明書';
COMMENT ON COLUMN tmd_attendance.late_comment IS '遅刻コメント';
COMMENT ON COLUMN tmd_attendance.leave_early_days IS '早退日数';
COMMENT ON COLUMN tmd_attendance.leave_early_thirty_minutes_or_more IS '早退30分以上日数';
COMMENT ON COLUMN tmd_attendance.leave_early_less_than_thirty_minutes IS '早退30分未満日数';
COMMENT ON COLUMN tmd_attendance.leave_early_time IS '早退時間';
COMMENT ON COLUMN tmd_attendance.actual_leave_early_time IS '実早退時間';
COMMENT ON COLUMN tmd_attendance.leave_early_thirty_minutes_or_more_time IS '早退30分以上時間';
COMMENT ON COLUMN tmd_attendance.leave_early_less_than_thirty_minutes_time IS '早退30分未満時間';
COMMENT ON COLUMN tmd_attendance.leave_early_reason IS '早退理由';
COMMENT ON COLUMN tmd_attendance.leave_early_certificate IS '早退証明書';
COMMENT ON COLUMN tmd_attendance.leave_early_comment IS '早退コメント';
COMMENT ON COLUMN tmd_attendance.work_time IS '勤務時間';
COMMENT ON COLUMN tmd_attendance.general_work_time IS '所定労働時間';
COMMENT ON COLUMN tmd_attendance.work_time_within_prescribed_work_time IS '所定労働時間内労働時間';
COMMENT ON COLUMN tmd_attendance.contract_work_time IS '契約勤務時間';
COMMENT ON COLUMN tmd_attendance.short_unpaid IS '無給時短時間';
COMMENT ON COLUMN tmd_attendance.rest_time IS '休憩時間';
COMMENT ON COLUMN tmd_attendance.over_rest_time IS '法定外休憩時間';
COMMENT ON COLUMN tmd_attendance.night_rest_time IS '深夜休憩時間';
COMMENT ON COLUMN tmd_attendance.legal_holiday_rest_time IS '法定休出休憩時間';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_rest_time IS '所定休出休憩時間';
COMMENT ON COLUMN tmd_attendance.public_time IS '公用外出時間';
COMMENT ON COLUMN tmd_attendance.private_time IS '私用外出時間';
COMMENT ON COLUMN tmd_attendance.minutely_holiday_a_time IS '分単位休暇A時間';
COMMENT ON COLUMN tmd_attendance.minutely_holiday_b_time IS '分単位休暇B時間';
COMMENT ON COLUMN tmd_attendance.minutely_holiday_a IS '分単位休暇A全休';
COMMENT ON COLUMN tmd_attendance.minutely_holiday_b IS '分単位休暇B全休';
COMMENT ON COLUMN tmd_attendance.times_overtime IS '残業回数';
COMMENT ON COLUMN tmd_attendance.overtime IS '残業時間';
COMMENT ON COLUMN tmd_attendance.overtime_before IS '前残業時間';
COMMENT ON COLUMN tmd_attendance.overtime_after IS '後残業時間';
COMMENT ON COLUMN tmd_attendance.overtime_in IS '法定内残業時間';
COMMENT ON COLUMN tmd_attendance.overtime_out IS '法定外残業時間';
COMMENT ON COLUMN tmd_attendance.workday_overtime_in IS '平日法定時間内残業時間';
COMMENT ON COLUMN tmd_attendance.workday_overtime_out IS '平日法定時間外残業時間';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_overtime_in IS '所定休日法定時間内残業時間';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_overtime_out IS '所定休日法定時間外残業時間';
COMMENT ON COLUMN tmd_attendance.late_night_time IS '深夜勤務時間';
COMMENT ON COLUMN tmd_attendance.night_work_within_prescribed_work IS '深夜所定労働時間内時間';
COMMENT ON COLUMN tmd_attendance.night_overtime_work IS '深夜時間外時間';
COMMENT ON COLUMN tmd_attendance.night_work_on_holiday IS '深夜休日労働時間';
COMMENT ON COLUMN tmd_attendance.specific_work_time IS '所定休日勤務時間';
COMMENT ON COLUMN tmd_attendance.legal_work_time IS '法定休日勤務時間';
COMMENT ON COLUMN tmd_attendance.decrease_time IS '減額対象時間';
COMMENT ON COLUMN tmd_attendance.time_comment IS '勤怠コメント';
COMMENT ON COLUMN tmd_attendance.remarks IS '備考';
COMMENT ON COLUMN tmd_attendance.work_days IS '出勤日数';
COMMENT ON COLUMN tmd_attendance.work_days_for_paid_leave IS '有給休暇用出勤日数';
COMMENT ON COLUMN tmd_attendance.total_work_days_for_paid_leave IS '有給休暇用全労働日';
COMMENT ON COLUMN tmd_attendance.times_holiday_work IS '休日出勤回数';
COMMENT ON COLUMN tmd_attendance.times_legal_holiday_work IS '法定休日出勤回数';
COMMENT ON COLUMN tmd_attendance.times_prescribed_holiday_work IS '所定休日出勤回数';
COMMENT ON COLUMN tmd_attendance.paid_leave_days IS '有給休暇日数';
COMMENT ON COLUMN tmd_attendance.paid_leave_hours IS '有給休暇時間数';
COMMENT ON COLUMN tmd_attendance.stock_leave_days IS 'ストック休暇日数';
COMMENT ON COLUMN tmd_attendance.compensation_days IS '代休日数';
COMMENT ON COLUMN tmd_attendance.legal_compensation_days IS '法定代休日数';
COMMENT ON COLUMN tmd_attendance.prescribed_compensation_days IS '所定代休日数';
COMMENT ON COLUMN tmd_attendance.night_compensation_days IS '深夜代休日数';
COMMENT ON COLUMN tmd_attendance.special_leave_days IS '特別休暇日数';
COMMENT ON COLUMN tmd_attendance.special_leave_hours IS '特別休暇時間数';
COMMENT ON COLUMN tmd_attendance.other_leave_days IS 'その他休暇日数';
COMMENT ON COLUMN tmd_attendance.other_leave_hours IS 'その他休暇時間数';
COMMENT ON COLUMN tmd_attendance.absence_days IS '欠勤日数';
COMMENT ON COLUMN tmd_attendance.absence_hours IS '欠勤時間数';
COMMENT ON COLUMN tmd_attendance.granted_legal_compensation_days IS '法定代休発生日数';
COMMENT ON COLUMN tmd_attendance.granted_prescribed_compensation_days IS '所定代休発生日数';
COMMENT ON COLUMN tmd_attendance.granted_night_compensation_days IS '深夜代休発生日数';
COMMENT ON COLUMN tmd_attendance.legal_holiday_work_time_with_compensation_day IS '法定休出時間(代休あり)';
COMMENT ON COLUMN tmd_attendance.legal_holiday_work_time_without_compensation_day IS '法定休出時間(代休なし)';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_work_time_with_compensation_day IS '所定休出時間(代休あり)';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_work_time_without_compensation_day IS '所定休出時間(代休なし)';
COMMENT ON COLUMN tmd_attendance.overtime_in_with_compensation_day IS '法定労働時間内残業時間(代休あり)';
COMMENT ON COLUMN tmd_attendance.overtime_in_without_compensation_day IS '法定労働時間内残業時間(代休なし)';
COMMENT ON COLUMN tmd_attendance.overtime_out_with_compensation_day IS '法定労働時間外残業時間(代休あり)';
COMMENT ON COLUMN tmd_attendance.overtime_out_without_compensation_day IS '法定労働時間外残業時間(代休なし)';
COMMENT ON COLUMN tmd_attendance.statutory_holiday_work_time_in IS '所定労働時間内法定休日労働時間';
COMMENT ON COLUMN tmd_attendance.statutory_holiday_work_time_out IS '所定労働時間外法定休日労働時間';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_work_time_in IS '所定労働時間内所定休日労働時間';
COMMENT ON COLUMN tmd_attendance.prescribed_holiday_work_time_out IS '所定労働時間外所定休日労働時間';
COMMENT ON COLUMN tmd_attendance.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_attendance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_attendance.insert_date IS '登録日';
COMMENT ON COLUMN tmd_attendance.insert_user IS '登録者';
COMMENT ON COLUMN tmd_attendance.update_date IS '更新日';
COMMENT ON COLUMN tmd_attendance.update_user IS '更新者';


CREATE TABLE tmd_attendance_correction
(
  tmd_attendance_correction_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  works integer NOT NULL DEFAULT 0,
  correction_times integer NOT NULL DEFAULT 0,
  correction_type character varying(50) NOT NULL DEFAULT ''::character varying,
  correction_date timestamp without time zone NOT NULL,
  correction_personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  correction_before text NOT NULL DEFAULT ''::character varying,
  correction_after text NOT NULL DEFAULT ''::character varying,
  correction_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_attendance_correction_pkey PRIMARY KEY (tmd_attendance_correction_id)
)
;
COMMENT ON TABLE tmd_attendance_correction IS '勤怠修正データ';
COMMENT ON COLUMN tmd_attendance_correction.tmd_attendance_correction_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_attendance_correction.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_attendance_correction.work_date IS '勤務日';
COMMENT ON COLUMN tmd_attendance_correction.works IS '勤務回数';
COMMENT ON COLUMN tmd_attendance_correction.correction_times IS '修正番号';
COMMENT ON COLUMN tmd_attendance_correction.correction_type IS '修正箇所';
COMMENT ON COLUMN tmd_attendance_correction.correction_date IS '修正日時';
COMMENT ON COLUMN tmd_attendance_correction.correction_personal_id IS '修正個人ID';
COMMENT ON COLUMN tmd_attendance_correction.correction_before IS '修正前';
COMMENT ON COLUMN tmd_attendance_correction.correction_after IS '修正後';
COMMENT ON COLUMN tmd_attendance_correction.correction_reason IS '修正理由';
COMMENT ON COLUMN tmd_attendance_correction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_attendance_correction.insert_date IS '登録日';
COMMENT ON COLUMN tmd_attendance_correction.insert_user IS '登録者';
COMMENT ON COLUMN tmd_attendance_correction.update_date IS '更新日';
COMMENT ON COLUMN tmd_attendance_correction.update_user IS '更新者';


CREATE TABLE tmd_allowance
(
  tmd_allowance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  works integer NOT NULL DEFAULT 0,
  allowance_code character varying(10) NOT NULL DEFAULT ''::character varying,
  allowance integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_allowance_pkey PRIMARY KEY (tmd_allowance_id)
)
;
COMMENT ON TABLE tmd_allowance IS '手当データ';
COMMENT ON COLUMN tmd_allowance.tmd_allowance_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_allowance.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_allowance.work_date IS '勤務日';
COMMENT ON COLUMN tmd_allowance.works IS '勤務回数';
COMMENT ON COLUMN tmd_allowance.allowance_code IS '手当コード';
COMMENT ON COLUMN tmd_allowance.allowance IS '手当';
COMMENT ON COLUMN tmd_allowance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_allowance.insert_date IS '登録日';
COMMENT ON COLUMN tmd_allowance.insert_user IS '登録者';
COMMENT ON COLUMN tmd_allowance.update_date IS '更新日';
COMMENT ON COLUMN tmd_allowance.update_user IS '更新者';


CREATE TABLE tmd_rest
(
  tmd_rest_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  rest integer NOT NULL DEFAULT 0,
  rest_start timestamp without time zone NOT NULL,
  rest_end timestamp without time zone NOT NULL,
  rest_time integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_rest_pkey PRIMARY KEY (tmd_rest_id)
)
;
COMMENT ON TABLE tmd_rest IS '休憩データ';
COMMENT ON COLUMN tmd_rest.tmd_rest_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_rest.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_rest.work_date IS '勤務日';
COMMENT ON COLUMN tmd_rest.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_rest.rest IS '休憩回数';
COMMENT ON COLUMN tmd_rest.rest_start IS '休憩開始時刻';
COMMENT ON COLUMN tmd_rest.rest_end IS '休憩終了時刻';
COMMENT ON COLUMN tmd_rest.rest_time IS '休憩時間';
COMMENT ON COLUMN tmd_rest.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_rest.insert_date IS '登録日';
COMMENT ON COLUMN tmd_rest.insert_user IS '登録者';
COMMENT ON COLUMN tmd_rest.update_date IS '更新日';
COMMENT ON COLUMN tmd_rest.update_user IS '更新者';


CREATE TABLE tmd_go_out
(
  tmd_go_out_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  go_out_type integer NOT NULL DEFAULT 0,
  times_go_out integer NOT NULL DEFAULT 0,
  go_out_start timestamp without time zone NOT NULL,
  go_out_end timestamp without time zone NOT NULL,
  go_out_time integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_go_out_pkey PRIMARY KEY (tmd_go_out_id)
)
;
COMMENT ON TABLE tmd_go_out IS '外出データ';
COMMENT ON COLUMN tmd_go_out.tmd_go_out_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_go_out.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_go_out.work_date IS '勤務日';
COMMENT ON COLUMN tmd_go_out.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_go_out.go_out_type IS '外出区分';
COMMENT ON COLUMN tmd_go_out.times_go_out IS '外出回数';
COMMENT ON COLUMN tmd_go_out.go_out_start IS '外出開始時刻';
COMMENT ON COLUMN tmd_go_out.go_out_end IS '外出終了時刻';
COMMENT ON COLUMN tmd_go_out.go_out_time IS '外出時間';
COMMENT ON COLUMN tmd_go_out.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_go_out.insert_date IS '登録日';
COMMENT ON COLUMN tmd_go_out.insert_user IS '登録者';
COMMENT ON COLUMN tmd_go_out.update_date IS '更新日';
COMMENT ON COLUMN tmd_go_out.update_user IS '更新者';


CREATE TABLE tmd_time_record
(
  tmd_time_record_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  record_type character varying(10) NOT NULL DEFAULT ''::character varying,
  record_time timestamp without time zone NOT NULL,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_time_record_pkey PRIMARY KEY (tmd_time_record_id)
)
;
COMMENT ON TABLE tmd_time_record IS '打刻データ';
COMMENT ON COLUMN tmd_time_record.tmd_time_record_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_time_record.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_time_record.work_date IS '勤務日';
COMMENT ON COLUMN tmd_time_record.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_time_record.record_type IS '打刻区分';
COMMENT ON COLUMN tmd_time_record.record_time IS '打刻時刻';
COMMENT ON COLUMN tmd_time_record.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_time_record.insert_date IS '登録日';
COMMENT ON COLUMN tmd_time_record.insert_user IS '登録者';
COMMENT ON COLUMN tmd_time_record.update_date IS '更新日';
COMMENT ON COLUMN tmd_time_record.update_user IS '更新者';

CREATE TABLE tmd_total_time
(
  tmd_total_time_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  calculation_date date NOT NULL,
  work_time integer NOT NULL DEFAULT 0,
  specific_work_time integer NOT NULL DEFAULT 0,
  contract_work_time integer NOT NULL DEFAULT 0,
  short_unpaid integer NOT NULL DEFAULT 0,
  times_work_date double precision NOT NULL DEFAULT 0,
  times_work integer NOT NULL DEFAULT 0,
  legal_work_on_holiday double precision NOT NULL DEFAULT 0,
  specific_work_on_holiday double precision NOT NULL DEFAULT 0,
  times_achievement integer NOT NULL DEFAULT 0,
  times_total_work_date integer NOT NULL DEFAULT 0,
  direct_start integer NOT NULL DEFAULT 0,
  direct_end integer NOT NULL DEFAULT 0,
  rest_time integer NOT NULL DEFAULT 0,
  rest_late_night integer NOT NULL DEFAULT 0,
  rest_work_on_specific_holiday integer NOT NULL DEFAULT 0,
  rest_work_on_holiday integer NOT NULL DEFAULT 0,
  public_time integer NOT NULL DEFAULT 0,
  private_time integer NOT NULL DEFAULT 0,
  minutely_holiday_a_time integer NOT NULL DEFAULT 0,
  minutely_holiday_b_time integer NOT NULL DEFAULT 0,
  overtime integer NOT NULL DEFAULT 0,
  overtime_in integer NOT NULL DEFAULT 0,
  overtime_out integer NOT NULL DEFAULT 0,
  late_night integer NOT NULL DEFAULT 0,
  night_work_within_prescribed_work integer NOT NULL DEFAULT 0,
  night_overtime_work integer NOT NULL DEFAULT 0,
  night_work_on_holiday integer NOT NULL DEFAULT 0,
  work_on_specific_holiday integer NOT NULL DEFAULT 0,
  work_on_holiday integer NOT NULL DEFAULT 0,
  decrease_time integer NOT NULL DEFAULT 0,
  forty_five_hour_overtime integer NOT NULL DEFAULT 0,
  times_overtime integer NOT NULL DEFAULT 0,
  times_working_holiday integer NOT NULL DEFAULT 0,
  late_days integer NOT NULL DEFAULT 0,
  late_thirty_minutes_or_more integer NOT NULL DEFAULT 0,
  late_less_than_thirty_minutes integer NOT NULL DEFAULT 0,
  late_time integer NOT NULL DEFAULT 0,
  late_thirty_minutes_or_more_time integer NOT NULL DEFAULT 0,
  late_less_than_thirty_minutes_time integer NOT NULL DEFAULT 0,
  times_late integer NOT NULL DEFAULT 0,
  leave_early_days integer NOT NULL DEFAULT 0,
  leave_early_thirty_minutes_or_more integer NOT NULL DEFAULT 0,
  leave_early_less_than_thirty_minutes integer NOT NULL DEFAULT 0,
  leave_early_time integer NOT NULL DEFAULT 0,
  leave_early_thirty_minutes_or_more_time integer NOT NULL DEFAULT 0,
  leave_early_less_than_thirty_minutes_time integer NOT NULL DEFAULT 0,
  times_leave_early integer NOT NULL DEFAULT 0,
  times_holiday integer NOT NULL DEFAULT 0,
  times_legal_holiday integer NOT NULL DEFAULT 0,
  times_specific_holiday integer NOT NULL DEFAULT 0,
  times_paid_holiday double precision NOT NULL DEFAULT 0,
  paid_holiday_hour integer NOT NULL DEFAULT 0,
  times_stock_holiday double precision NOT NULL DEFAULT 0,
  times_compensation double precision NOT NULL DEFAULT 0,
  times_legal_compensation double precision NOT NULL DEFAULT 0,
  times_specific_compensation double precision NOT NULL DEFAULT 0,
  times_late_compensation double precision NOT NULL DEFAULT 0,
  times_holiday_substitute double precision NOT NULL DEFAULT 0,
  times_legal_holiday_substitute double precision NOT NULL DEFAULT 0,
  times_specific_holiday_substitute double precision NOT NULL DEFAULT 0,
  total_special_holiday double precision NOT NULL DEFAULT 0,
  special_holiday_hour integer NOT NULL DEFAULT 0,
  total_other_holiday double precision NOT NULL DEFAULT 0,
  other_holiday_hour integer NOT NULL DEFAULT 0,
  total_absence double precision NOT NULL DEFAULT 0,
  absence_hour integer NOT NULL DEFAULT 0,
  total_allowance integer NOT NULL DEFAULT 0,
  sixty_hour_overtime integer NOT NULL DEFAULT 0,
  week_day_overtime integer NOT NULL DEFAULT 0,
  specific_overtime integer NOT NULL DEFAULT 0,
  times_alternative double precision NOT NULL DEFAULT 0,
  legal_compensation_occurred double precision NOT NULL DEFAULT 0,
  specific_compensation_occurred double precision NOT NULL DEFAULT 0,
  late_compensation_occurred double precision NOT NULL DEFAULT 0,
  legal_compensation_unused double precision NOT NULL DEFAULT 0,
  specific_compensation_unused double precision NOT NULL DEFAULT 0,
  late_compensation_unused double precision NOT NULL DEFAULT 0,
  statutory_holiday_work_time_in integer NOT NULL DEFAULT 0,
  statutory_holiday_work_time_out integer NOT NULL DEFAULT 0,
  prescribed_holiday_work_time_in integer NOT NULL DEFAULT 0,
  prescribed_holiday_work_time_out integer NOT NULL DEFAULT 0,
  weekly_over_forty_hour_work_time integer NOT NULL DEFAULT 0,
  overtime_in_no_weekly_forty integer NOT NULL DEFAULT 0,
  overtime_out_no_weekly_forty integer NOT NULL DEFAULT 0,
  week_day_overtime_total integer NOT NULL DEFAULT 0,
  week_day_overtime_in_no_weekly_forty integer NOT NULL DEFAULT 0,
  week_day_overtime_out_no_weekly_forty integer NOT NULL DEFAULT 0,
  week_day_overtime_in integer NOT NULL DEFAULT 0,
  general_int_item1 integer NOT NULL DEFAULT 0,
  general_int_item2 integer NOT NULL DEFAULT 0,
  general_int_item3 integer NOT NULL DEFAULT 0,
  general_int_item4 integer NOT NULL DEFAULT 0,
  general_int_item5 integer NOT NULL DEFAULT 0,
  general_double_item1 double precision NOT NULL DEFAULT 0, 
  general_double_item2 double precision NOT NULL DEFAULT 0, 
  general_double_item3 double precision NOT NULL DEFAULT 0, 
  general_double_item4 double precision NOT NULL DEFAULT 0, 
  general_double_item5 double precision NOT NULL DEFAULT 0, 
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_total_time_pkey PRIMARY KEY (tmd_total_time_id)
)
;
COMMENT ON TABLE tmd_total_time IS '勤怠集計データ';
COMMENT ON COLUMN tmd_total_time.tmd_total_time_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_total_time.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_total_time.calculation_year IS '年';
COMMENT ON COLUMN tmd_total_time.calculation_month IS '月';
COMMENT ON COLUMN tmd_total_time.calculation_date IS '集計日';
COMMENT ON COLUMN tmd_total_time.work_time IS '勤務時間';
COMMENT ON COLUMN tmd_total_time.specific_work_time IS '所定勤務時間';
COMMENT ON COLUMN tmd_total_time.contract_work_time IS '契約勤務時間';
COMMENT ON COLUMN tmd_total_time.short_unpaid IS '無給時短時間';
COMMENT ON COLUMN tmd_total_time.times_work_date IS '出勤日数';
COMMENT ON COLUMN tmd_total_time.times_work IS '出勤回数';
COMMENT ON COLUMN tmd_total_time.legal_work_on_holiday IS '法定休日出勤日数';
COMMENT ON COLUMN tmd_total_time.specific_work_on_holiday IS '所定休日出勤日数';
COMMENT ON COLUMN tmd_total_time.times_achievement IS '出勤実績日数';
COMMENT ON COLUMN tmd_total_time.times_total_work_date IS '出勤対象日数';
COMMENT ON COLUMN tmd_total_time.direct_start IS '直行回数';
COMMENT ON COLUMN tmd_total_time.direct_end IS '直帰回数';
COMMENT ON COLUMN tmd_total_time.rest_time IS '休憩時間';
COMMENT ON COLUMN tmd_total_time.rest_late_night IS '深夜休憩時間';
COMMENT ON COLUMN tmd_total_time.rest_work_on_specific_holiday IS '所定休出休憩時間';
COMMENT ON COLUMN tmd_total_time.rest_work_on_holiday IS '法定休出休憩時間';
COMMENT ON COLUMN tmd_total_time.public_time IS '公用外出時間';
COMMENT ON COLUMN tmd_total_time.private_time IS '私用外出時間';
COMMENT ON COLUMN tmd_total_time.minutely_holiday_a_time IS '分単位休暇A時間';
COMMENT ON COLUMN tmd_total_time.minutely_holiday_b_time IS '分単位休暇B時間';
COMMENT ON COLUMN tmd_total_time.overtime IS '残業時間';
COMMENT ON COLUMN tmd_total_time.overtime_in IS '法定内残業時間';
COMMENT ON COLUMN tmd_total_time.overtime_out IS '法定外残業時間';
COMMENT ON COLUMN tmd_total_time.late_night IS '深夜時間';
COMMENT ON COLUMN tmd_total_time.night_work_within_prescribed_work IS '深夜所定労働時間内時間';
COMMENT ON COLUMN tmd_total_time.night_overtime_work IS '深夜時間外時間';
COMMENT ON COLUMN tmd_total_time.night_work_on_holiday IS '深夜休日労働時間';
COMMENT ON COLUMN tmd_total_time.work_on_specific_holiday IS '所定休出時間';
COMMENT ON COLUMN tmd_total_time.work_on_holiday IS '法定休出時間';
COMMENT ON COLUMN tmd_total_time.decrease_time IS '減額対象時間';
COMMENT ON COLUMN tmd_total_time.forty_five_hour_overtime IS '45時間超残業時間';
COMMENT ON COLUMN tmd_total_time.times_overtime IS '残業回数';
COMMENT ON COLUMN tmd_total_time.times_working_holiday IS '休日出勤回数';
COMMENT ON COLUMN tmd_total_time.late_days IS '遅刻合計日数';
COMMENT ON COLUMN tmd_total_time.late_thirty_minutes_or_more IS '遅刻30分以上日数';
COMMENT ON COLUMN tmd_total_time.late_less_than_thirty_minutes IS '遅刻30分未満日数';
COMMENT ON COLUMN tmd_total_time.late_time IS '遅刻合計時間';
COMMENT ON COLUMN tmd_total_time.late_thirty_minutes_or_more_time IS '遅刻30分以上時間';
COMMENT ON COLUMN tmd_total_time.late_less_than_thirty_minutes_time IS '遅刻30分未満時間';
COMMENT ON COLUMN tmd_total_time.times_late IS '遅刻合計回数';
COMMENT ON COLUMN tmd_total_time.leave_early_days IS '早退合計日数';
COMMENT ON COLUMN tmd_total_time.leave_early_thirty_minutes_or_more IS '早退30分以上日数';
COMMENT ON COLUMN tmd_total_time.leave_early_less_than_thirty_minutes IS '早退30分未満日数';
COMMENT ON COLUMN tmd_total_time.leave_early_time IS '早退合計時間';
COMMENT ON COLUMN tmd_total_time.leave_early_thirty_minutes_or_more_time IS '早退30分以上時間';
COMMENT ON COLUMN tmd_total_time.leave_early_less_than_thirty_minutes_time IS '早退30分未満時間';
COMMENT ON COLUMN tmd_total_time.times_leave_early IS '早退合計回数';
COMMENT ON COLUMN tmd_total_time.times_holiday IS '合計休日日数';
COMMENT ON COLUMN tmd_total_time.times_legal_holiday IS '法定休日日数';
COMMENT ON COLUMN tmd_total_time.times_specific_holiday IS '所定休日日数';
COMMENT ON COLUMN tmd_total_time.times_paid_holiday IS '有給休暇日数';
COMMENT ON COLUMN tmd_total_time.paid_holiday_hour IS '有給休暇時間';
COMMENT ON COLUMN tmd_total_time.times_stock_holiday IS 'ストック休暇日数';
COMMENT ON COLUMN tmd_total_time.times_compensation IS '合計代休日数';
COMMENT ON COLUMN tmd_total_time.times_legal_compensation IS '法定代休日数';
COMMENT ON COLUMN tmd_total_time.times_specific_compensation IS '所定代休日数';
COMMENT ON COLUMN tmd_total_time.times_late_compensation IS '深夜代休日数';
COMMENT ON COLUMN tmd_total_time.times_holiday_substitute IS '合計振替休日日数';
COMMENT ON COLUMN tmd_total_time.times_legal_holiday_substitute IS '法定振替休日日数';
COMMENT ON COLUMN tmd_total_time.times_specific_holiday_substitute IS '所定振替休日日数';
COMMENT ON COLUMN tmd_total_time.total_special_holiday IS '特別休暇合計日数';
COMMENT ON COLUMN tmd_total_time.special_holiday_hour IS '特別休暇時間';
COMMENT ON COLUMN tmd_total_time.total_other_holiday IS 'その他休暇合計日数';
COMMENT ON COLUMN tmd_total_time.other_holiday_hour IS 'その他休暇時間';
COMMENT ON COLUMN tmd_total_time.total_absence IS '欠勤合計日数';
COMMENT ON COLUMN tmd_total_time.absence_hour IS '欠勤時間';
COMMENT ON COLUMN tmd_total_time.total_allowance IS '手当合計';
COMMENT ON COLUMN tmd_total_time.sixty_hour_overtime IS '60時間超残業時間';
COMMENT ON COLUMN tmd_total_time.week_day_overtime IS '平日時間外時間';
COMMENT ON COLUMN tmd_total_time.specific_overtime IS '所定休日時間外時間';
COMMENT ON COLUMN tmd_total_time.times_alternative IS '代替休暇日数';
COMMENT ON COLUMN tmd_total_time.legal_compensation_occurred IS '法定代休発生日数';
COMMENT ON COLUMN tmd_total_time.specific_compensation_occurred IS '所定代休発生日数';
COMMENT ON COLUMN tmd_total_time.late_compensation_occurred IS '深夜代休発生日数';
COMMENT ON COLUMN tmd_total_time.legal_compensation_unused IS '法定代休未取得日数';
COMMENT ON COLUMN tmd_total_time.specific_compensation_unused IS '所定代休未取得日数';
COMMENT ON COLUMN tmd_total_time.late_compensation_unused IS '深夜代休未取得日数';
COMMENT ON COLUMN tmd_total_time.statutory_holiday_work_time_in IS '所定労働時間内法定休日労働時間';
COMMENT ON COLUMN tmd_total_time.statutory_holiday_work_time_out IS '所定労働時間外法定休日労働時間';
COMMENT ON COLUMN tmd_total_time.prescribed_holiday_work_time_in IS '所定労働時間内所定休日労働時間';
COMMENT ON COLUMN tmd_total_time.prescribed_holiday_work_time_out IS '所定労働時間外所定休日労働時間';
COMMENT ON COLUMN tmd_total_time.weekly_over_forty_hour_work_time IS '週40時間超勤務時間';
COMMENT ON COLUMN tmd_total_time.overtime_in_no_weekly_forty IS '法定内残業時間(週40時間超除く)';
COMMENT ON COLUMN tmd_total_time.overtime_out_no_weekly_forty IS '法定外残業時間(週40時間超除く)';
COMMENT ON COLUMN tmd_total_time.week_day_overtime_total IS '平日残業合計時間';
COMMENT ON COLUMN tmd_total_time.week_day_overtime_in_no_weekly_forty IS '平日時間内時間(週40時間超除く)';
COMMENT ON COLUMN tmd_total_time.week_day_overtime_out_no_weekly_forty IS '平日時間外時間(週40時間超除く)';
COMMENT ON COLUMN tmd_total_time.week_day_overtime_in IS '平日時間内時間';
COMMENT ON COLUMN tmd_total_time.general_int_item1 IS '汎用項目1(数値)';
COMMENT ON COLUMN tmd_total_time.general_int_item2 IS '汎用項目2(数値)';
COMMENT ON COLUMN tmd_total_time.general_int_item3 IS '汎用項目3(数値)';
COMMENT ON COLUMN tmd_total_time.general_int_item4 IS '汎用項目4(数値)';
COMMENT ON COLUMN tmd_total_time.general_int_item5 IS '汎用項目5(数値)';
COMMENT ON COLUMN tmd_total_time.general_double_item1 IS '汎用項目1(浮動小数点数)';
COMMENT ON COLUMN tmd_total_time.general_double_item2 IS '汎用項目2(浮動小数点数)';
COMMENT ON COLUMN tmd_total_time.general_double_item3 IS '汎用項目3(浮動小数点数)';
COMMENT ON COLUMN tmd_total_time.general_double_item4 IS '汎用項目4(浮動小数点数)';
COMMENT ON COLUMN tmd_total_time.general_double_item5 IS '汎用項目5(浮動小数点数)';
COMMENT ON COLUMN tmd_total_time.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_total_time.insert_date IS '登録日';
COMMENT ON COLUMN tmd_total_time.insert_user IS '登録者';
COMMENT ON COLUMN tmd_total_time.update_date IS '更新日';
COMMENT ON COLUMN tmd_total_time.update_user IS '更新者';


CREATE TABLE tmd_total_time_correction
(
  tmd_total_time_correction_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  correction_times integer NOT NULL DEFAULT 0,
  correction_date timestamp without time zone NOT NULL,
  correction_personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  correction_type character varying(50) NOT NULL DEFAULT ''::character varying,
  correction_before text NOT NULL DEFAULT ''::character varying,
  correction_after text NOT NULL DEFAULT ''::character varying,
  correction_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_total_time_correction_pkey PRIMARY KEY (tmd_total_time_correction_id)
)
;
COMMENT ON TABLE tmd_total_time_correction IS '勤怠集計修正データ';
COMMENT ON COLUMN tmd_total_time_correction.tmd_total_time_correction_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_total_time_correction.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_total_time_correction.calculation_year IS '年';
COMMENT ON COLUMN tmd_total_time_correction.calculation_month IS '月';
COMMENT ON COLUMN tmd_total_time_correction.correction_times IS '修正番号';
COMMENT ON COLUMN tmd_total_time_correction.correction_date IS '修正日時';
COMMENT ON COLUMN tmd_total_time_correction.correction_personal_id IS '修正個人ID';
COMMENT ON COLUMN tmd_total_time_correction.correction_type IS '修正箇所';
COMMENT ON COLUMN tmd_total_time_correction.correction_before IS '修正前';
COMMENT ON COLUMN tmd_total_time_correction.correction_after IS '修正後';
COMMENT ON COLUMN tmd_total_time_correction.correction_reason IS '修正理由';
COMMENT ON COLUMN tmd_total_time_correction.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_total_time_correction.insert_date IS '登録日';
COMMENT ON COLUMN tmd_total_time_correction.insert_user IS '登録者';
COMMENT ON COLUMN tmd_total_time_correction.update_date IS '更新日';
COMMENT ON COLUMN tmd_total_time_correction.update_user IS '更新者';


CREATE TABLE tmd_total_leave
(
  tmd_total_leave_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  times double precision NOT NULL DEFAULT 0,
  hours integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_total_leave_pkey PRIMARY KEY (tmd_total_leave_id)
)
;
COMMENT ON TABLE tmd_total_leave IS '特別休暇集計データ';
COMMENT ON COLUMN tmd_total_leave.tmd_total_leave_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_total_leave.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_total_leave.calculation_year IS '年';
COMMENT ON COLUMN tmd_total_leave.calculation_month IS '月';
COMMENT ON COLUMN tmd_total_leave.holiday_code IS '休暇コード';
COMMENT ON COLUMN tmd_total_leave.times IS '特別休暇日数';
COMMENT ON COLUMN tmd_total_leave.hours IS '特別休暇時間数';
COMMENT ON COLUMN tmd_total_time.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_total_time.insert_date IS '登録日';
COMMENT ON COLUMN tmd_total_time.insert_user IS '登録者';
COMMENT ON COLUMN tmd_total_time.update_date IS '更新日';
COMMENT ON COLUMN tmd_total_time.update_user IS '更新者';


CREATE TABLE tmd_total_other_vacation
(
  tmd_total_other_vacation_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  times double precision NOT NULL DEFAULT 0,
  hours integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_total_other_vacation_pkey PRIMARY KEY (tmd_total_other_vacation_id)
)
;
COMMENT ON TABLE tmd_total_other_vacation IS 'その他休暇集計データ';
COMMENT ON COLUMN tmd_total_other_vacation.tmd_total_other_vacation_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_total_other_vacation.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_total_other_vacation.calculation_year IS '年';
COMMENT ON COLUMN tmd_total_other_vacation.calculation_month IS '月';
COMMENT ON COLUMN tmd_total_other_vacation.holiday_code IS '休暇コード';
COMMENT ON COLUMN tmd_total_other_vacation.times IS 'その他休暇日数';
COMMENT ON COLUMN tmd_total_other_vacation.hours IS 'その他休暇時間数';
COMMENT ON COLUMN tmd_total_other_vacation.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_total_other_vacation.insert_date IS '登録日';
COMMENT ON COLUMN tmd_total_other_vacation.insert_user IS '登録者';
COMMENT ON COLUMN tmd_total_other_vacation.update_date IS '更新日';
COMMENT ON COLUMN tmd_total_other_vacation.update_user IS '更新者';


CREATE TABLE tmd_total_absence
(
  tmd_total_absence_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  absence_code character varying(10) NOT NULL DEFAULT ''::character varying,
  times double precision NOT NULL DEFAULT 0,
  hours integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_total_absence_pkey PRIMARY KEY (tmd_total_absence_id)
)
;
COMMENT ON TABLE tmd_total_absence IS '欠勤集計データ';
COMMENT ON COLUMN tmd_total_absence.tmd_total_absence_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_total_absence.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_total_absence.calculation_year IS '年';
COMMENT ON COLUMN tmd_total_absence.calculation_month IS '月';
COMMENT ON COLUMN tmd_total_absence.absence_code IS '欠勤コード';
COMMENT ON COLUMN tmd_total_absence.times IS '欠勤日数';
COMMENT ON COLUMN tmd_total_absence.hours IS '欠勤時間数';
COMMENT ON COLUMN tmd_total_absence.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_total_absence.insert_date IS '登録日';
COMMENT ON COLUMN tmd_total_absence.insert_user IS '登録者';
COMMENT ON COLUMN tmd_total_absence.update_date IS '更新日';
COMMENT ON COLUMN tmd_total_absence.update_user IS '更新者';


CREATE TABLE tmd_total_allowance
(
  tmd_total_allowance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  allowance_code character varying(10) NOT NULL DEFAULT ''::character varying,
  times integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_total_allowance_pkey PRIMARY KEY (tmd_total_allowance_id)
)
;
COMMENT ON TABLE tmd_total_allowance IS '手当集計データ';
COMMENT ON COLUMN tmd_total_allowance.tmd_total_allowance_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_total_allowance.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_total_allowance.calculation_year IS '年';
COMMENT ON COLUMN tmd_total_allowance.calculation_month IS '月';
COMMENT ON COLUMN tmd_total_allowance.allowance_code IS '手当コード';
COMMENT ON COLUMN tmd_total_allowance.times IS '手当回数';
COMMENT ON COLUMN tmd_total_allowance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_total_allowance.insert_date IS '登録日';
COMMENT ON COLUMN tmd_total_allowance.insert_user IS '登録者';
COMMENT ON COLUMN tmd_total_allowance.update_date IS '更新日';
COMMENT ON COLUMN tmd_total_allowance.update_user IS '更新者';


CREATE TABLE tmt_total_time
(
  tmt_total_time_id bigint NOT NULL DEFAULT 0,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  cutoff_code character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_date date NOT NULL,
  cutoff_state integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmt_total_time_pkey PRIMARY KEY (tmt_total_time_id)
)
;
COMMENT ON TABLE tmt_total_time IS '勤怠集計管理';
COMMENT ON COLUMN tmt_total_time.tmt_total_time_id IS 'レコード識別ID';
COMMENT ON COLUMN tmt_total_time.calculation_year IS '集計年';
COMMENT ON COLUMN tmt_total_time.calculation_month IS '集計月';
COMMENT ON COLUMN tmt_total_time.cutoff_code IS '締日コード';
COMMENT ON COLUMN tmt_total_time.calculation_date IS '集計日';
COMMENT ON COLUMN tmt_total_time.cutoff_state IS '締状態';
COMMENT ON COLUMN tmt_total_time.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmt_total_time.insert_date IS '登録日';
COMMENT ON COLUMN tmt_total_time.insert_user IS '登録者';
COMMENT ON COLUMN tmt_total_time.update_date IS '更新日';
COMMENT ON COLUMN tmt_total_time.update_user IS '更新者';


CREATE TABLE tmt_total_time_employee
(
  tmt_total_time_employee_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_year integer NOT NULL DEFAULT 0,
  calculation_month integer NOT NULL DEFAULT 0,
  cutoff_code character varying(10) NOT NULL DEFAULT ''::character varying,
  calculation_date date NOT NULL,
  cutoff_state integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmt_total_time_employee_pkey PRIMARY KEY (tmt_total_time_employee_id)
)
;
COMMENT ON TABLE tmt_total_time_employee IS '社員勤怠集計管理';
COMMENT ON COLUMN tmt_total_time_employee.tmt_total_time_employee_id IS 'レコード識別ID';
COMMENT ON COLUMN tmt_total_time_employee.personal_id IS '個人ID';
COMMENT ON COLUMN tmt_total_time_employee.calculation_year IS '集計年';
COMMENT ON COLUMN tmt_total_time_employee.calculation_month IS '集計月';
COMMENT ON COLUMN tmt_total_time_employee.cutoff_code IS '締日コード';
COMMENT ON COLUMN tmt_total_time_employee.calculation_date IS '集計日';
COMMENT ON COLUMN tmt_total_time_employee.cutoff_state IS '締状態';
COMMENT ON COLUMN tmt_total_time_employee.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmt_total_time_employee.insert_date IS '登録日';
COMMENT ON COLUMN tmt_total_time_employee.insert_user IS '登録者';
COMMENT ON COLUMN tmt_total_time_employee.update_date IS '更新日';
COMMENT ON COLUMN tmt_total_time_employee.update_user IS '更新者';


CREATE TABLE tmd_overtime_request
(
  tmd_overtime_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  overtime_type integer NOT NULL DEFAULT 0,
  request_time integer NOT NULL DEFAULT 0,
  request_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_overtime_request_pkey PRIMARY KEY (tmd_overtime_request_id)
)
;
COMMENT ON TABLE tmd_overtime_request IS '残業申請';
COMMENT ON COLUMN tmd_overtime_request.tmd_overtime_request_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_overtime_request.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_overtime_request.request_date IS '申請日';
COMMENT ON COLUMN tmd_overtime_request.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_overtime_request.overtime_type IS '残業区分';
COMMENT ON COLUMN tmd_overtime_request.request_time IS '申請時間';
COMMENT ON COLUMN tmd_overtime_request.request_reason IS '理由';
COMMENT ON COLUMN tmd_overtime_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_overtime_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_overtime_request.insert_date IS '登録日';
COMMENT ON COLUMN tmd_overtime_request.insert_user IS '登録者';
COMMENT ON COLUMN tmd_overtime_request.update_date IS '更新日';
COMMENT ON COLUMN tmd_overtime_request.update_user IS '更新者';


CREATE TABLE tmd_holiday_request
(
  tmd_holiday_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_start_date date NOT NULL,
  request_end_date date NOT NULL,
  holiday_type1 integer NOT NULL DEFAULT 0,
  holiday_type2 character varying(10) NOT NULL DEFAULT ''::character varying,
  holiday_range integer NOT NULL DEFAULT 0,
  start_time timestamp without time zone NOT NULL,
  end_time timestamp without time zone NOT NULL,
  holiday_acquisition_date date NOT NULL,
  use_day double precision NOT NULL DEFAULT 0,
  use_hour integer NOT NULL DEFAULT 0,
  request_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_holiday_request_pkey PRIMARY KEY (tmd_holiday_request_id)
)
;
COMMENT ON TABLE tmd_holiday_request IS '休暇申請';
COMMENT ON COLUMN tmd_holiday_request.tmd_holiday_request_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_holiday_request.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_holiday_request.request_start_date IS '申請開始日';
COMMENT ON COLUMN tmd_holiday_request.request_end_date IS '申請終了日';
COMMENT ON COLUMN tmd_holiday_request.holiday_type1 IS '休暇種別1';
COMMENT ON COLUMN tmd_holiday_request.holiday_type2 IS '休暇種別2';
COMMENT ON COLUMN tmd_holiday_request.holiday_range IS '休暇範囲';
COMMENT ON COLUMN tmd_holiday_request.start_time IS '時休開始時刻';
COMMENT ON COLUMN tmd_holiday_request.end_time IS '時休終了時刻';
COMMENT ON COLUMN tmd_holiday_request.holiday_acquisition_date IS '休暇取得日';
COMMENT ON COLUMN tmd_holiday_request.use_day IS '使用日数';
COMMENT ON COLUMN tmd_holiday_request.use_hour IS '使用時間数';
COMMENT ON COLUMN tmd_holiday_request.request_reason IS '理由';
COMMENT ON COLUMN tmd_holiday_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_holiday_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_holiday_request.insert_date IS '登録日';
COMMENT ON COLUMN tmd_holiday_request.insert_user IS '登録者';
COMMENT ON COLUMN tmd_holiday_request.update_date IS '更新日';
COMMENT ON COLUMN tmd_holiday_request.update_user IS '更新者';


CREATE TABLE tmd_work_on_holiday_request
(
  tmd_work_on_holiday_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  work_on_holiday_type character varying(20) NOT NULL DEFAULT ''::character varying,
  substitute integer NOT NULL DEFAULT 0,
  work_type_code character varying(10) NOT NULL DEFAULT ''::character varying,
  start_time timestamp without time zone,
  end_time timestamp without time zone,
  request_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_work_on_holiday_request_pkey PRIMARY KEY (tmd_work_on_holiday_request_id)
)
;
COMMENT ON TABLE tmd_work_on_holiday_request IS '休日出勤申請';
COMMENT ON COLUMN tmd_work_on_holiday_request.tmd_work_on_holiday_request_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_work_on_holiday_request.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_work_on_holiday_request.request_date IS '出勤日';
COMMENT ON COLUMN tmd_work_on_holiday_request.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_work_on_holiday_request.work_on_holiday_type IS '休出種別';
COMMENT ON COLUMN tmd_work_on_holiday_request.substitute IS '振替申請';
COMMENT ON COLUMN tmd_work_on_holiday_request.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmd_work_on_holiday_request.start_time IS '出勤予定時刻';
COMMENT ON COLUMN tmd_work_on_holiday_request.end_time IS '退勤予定時刻';
COMMENT ON COLUMN tmd_work_on_holiday_request.request_reason IS '理由';
COMMENT ON COLUMN tmd_work_on_holiday_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_work_on_holiday_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_work_on_holiday_request.insert_date IS '登録日';
COMMENT ON COLUMN tmd_work_on_holiday_request.insert_user IS '登録者';
COMMENT ON COLUMN tmd_work_on_holiday_request.update_date IS '更新日';
COMMENT ON COLUMN tmd_work_on_holiday_request.update_user IS '更新者';


CREATE TABLE tmd_sub_holiday_request
(
  tmd_sub_holiday_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_date date NOT NULL,
  holiday_range integer NOT NULL DEFAULT 0,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  work_date_sub_holiday_type integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_sub_holiday_request_pkey PRIMARY KEY (tmd_sub_holiday_request_id)
)
;
COMMENT ON TABLE tmd_sub_holiday_request IS '代休申請';
COMMENT ON COLUMN tmd_sub_holiday_request.tmd_sub_holiday_request_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_sub_holiday_request.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_sub_holiday_request.request_date IS '代休日';
COMMENT ON COLUMN tmd_sub_holiday_request.holiday_range IS '休暇範囲';
COMMENT ON COLUMN tmd_sub_holiday_request.work_date IS '出勤日';
COMMENT ON COLUMN tmd_sub_holiday_request.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_sub_holiday_request.work_date_sub_holiday_type IS '代休種別';
COMMENT ON COLUMN tmd_sub_holiday_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_sub_holiday_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_sub_holiday_request.insert_date IS '登録日';
COMMENT ON COLUMN tmd_sub_holiday_request.insert_user IS '登録者';
COMMENT ON COLUMN tmd_sub_holiday_request.update_date IS '更新日';
COMMENT ON COLUMN tmd_sub_holiday_request.update_user IS '更新者';


CREATE TABLE tmd_work_type_change_request
(
  tmd_work_type_change_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  work_type_code character varying(10) NOT NULL DEFAULT ''::character varying,
  request_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_work_type_change_request_pkey PRIMARY KEY (tmd_work_type_change_request_id)
)
;
COMMENT ON TABLE tmd_work_type_change_request IS '勤務形態変更申請';
COMMENT ON COLUMN tmd_work_type_change_request.tmd_work_type_change_request_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_work_type_change_request.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_work_type_change_request.request_date IS '出勤日';
COMMENT ON COLUMN tmd_work_type_change_request.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_work_type_change_request.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmd_work_type_change_request.request_reason IS '理由';
COMMENT ON COLUMN tmd_work_type_change_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_work_type_change_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_work_type_change_request.insert_date IS '登録日';
COMMENT ON COLUMN tmd_work_type_change_request.insert_user IS '登録者';
COMMENT ON COLUMN tmd_work_type_change_request.update_date IS '更新日';
COMMENT ON COLUMN tmd_work_type_change_request.update_user IS '更新者';


CREATE TABLE tmd_difference_request
(
  tmd_difference_request_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  request_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  difference_type character varying(10) NOT NULL DEFAULT ''::character varying,
  work_type_code character varying(10) NOT NULL DEFAULT ''::character varying,
  start_date integer NOT NULL DEFAULT 0,
  request_start timestamp without time zone NOT NULL,
  request_end timestamp without time zone NOT NULL,
  request_reason character varying(50) NOT NULL DEFAULT ''::character varying,
  workflow bigint NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_difference_request_pkey PRIMARY KEY (tmd_difference_request_id)
)
;
COMMENT ON TABLE tmd_difference_request IS '時差出勤申請';
COMMENT ON COLUMN tmd_difference_request.tmd_difference_request_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_difference_request.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_difference_request.request_date IS '時差出勤日';
COMMENT ON COLUMN tmd_difference_request.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_difference_request.difference_type IS '時差出勤区分';
COMMENT ON COLUMN tmd_difference_request.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmd_difference_request.start_date IS '開始日';
COMMENT ON COLUMN tmd_difference_request.request_start IS '時差出勤開始時刻';
COMMENT ON COLUMN tmd_difference_request.request_end IS '時差出勤終了時刻';
COMMENT ON COLUMN tmd_difference_request.request_reason IS '理由';
COMMENT ON COLUMN tmd_difference_request.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_difference_request.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_difference_request.insert_date IS '登録日';
COMMENT ON COLUMN tmd_difference_request.insert_user IS '登録者';
COMMENT ON COLUMN tmd_difference_request.update_date IS '更新日';
COMMENT ON COLUMN tmd_difference_request.update_user IS '更新者';


CREATE TABLE tmd_substitute
(
  tmd_substitute_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  substitute_date date,
  substitute_type character varying(20) NOT NULL DEFAULT ''::character varying,
  substitute_range integer NOT NULL DEFAULT 0,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  workflow bigint NOT NULL DEFAULT 0,
  transition_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_substitute_pkey PRIMARY KEY (tmd_substitute_id)
)
;
COMMENT ON TABLE tmd_substitute IS '振替休日データ';
COMMENT ON COLUMN tmd_substitute.tmd_substitute_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_substitute.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_substitute.substitute_date IS '振替日';
COMMENT ON COLUMN tmd_substitute.substitute_type IS '振替種別';
COMMENT ON COLUMN tmd_substitute.substitute_range IS '振替範囲';
COMMENT ON COLUMN tmd_substitute.work_date IS '出勤日';
COMMENT ON COLUMN tmd_substitute.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_substitute.workflow IS 'ワークフロー番号';
COMMENT ON COLUMN tmd_substitute.transition_flag IS '移行フラグ';
COMMENT ON COLUMN tmd_substitute.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_substitute.insert_date IS '登録日';
COMMENT ON COLUMN tmd_substitute.insert_user IS '登録者';
COMMENT ON COLUMN tmd_substitute.update_date IS '更新日';
COMMENT ON COLUMN tmd_substitute.update_user IS '更新者';



CREATE TABLE tmd_sub_holiday
(
  tmd_sub_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  times_work integer NOT NULL DEFAULT 0,
  sub_holiday_type integer NOT NULL DEFAULT 0,
  sub_holiday_days double precision NOT NULL DEFAULT 0,
  transition_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_sub_holiday_pkey PRIMARY KEY (tmd_sub_holiday_id)
)
;
COMMENT ON TABLE tmd_sub_holiday IS '代休データ';
COMMENT ON COLUMN tmd_sub_holiday.tmd_sub_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_sub_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_sub_holiday.work_date IS '出勤日';
COMMENT ON COLUMN tmd_sub_holiday.times_work IS '勤務回数';
COMMENT ON COLUMN tmd_sub_holiday.sub_holiday_type IS '代休種別';
COMMENT ON COLUMN tmd_sub_holiday.sub_holiday_days IS '代休日数';
COMMENT ON COLUMN tmd_sub_holiday.transition_flag IS '移行フラグ';
COMMENT ON COLUMN tmd_sub_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_sub_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmd_sub_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmd_sub_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmd_sub_holiday.update_user IS '更新者';


CREATE TABLE tmm_time_setting
(
  tmm_time_setting_id bigint NOT NULL DEFAULT 0,
  work_setting_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  work_setting_name character varying(50) NOT NULL DEFAULT ''::character varying,
  work_setting_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  cutoff_code character varying(10) NOT NULL DEFAULT ''::character varying,
  time_management_flag integer NOT NULL DEFAULT 0,
  daily_approval_flag integer NOT NULL DEFAULT 0,
  before_overtime_flag integer NOT NULL DEFAULT 0,
  specific_holiday_handling integer NOT NULL DEFAULT 0,
  portal_time_buttons integer NOT NULL DEFAULT 0,
  portal_rest_buttons integer NOT NULL DEFAULT 0,
  use_scheduled_time integer NOT NULL DEFAULT 0,
  round_daily_start_unit integer NOT NULL DEFAULT 0,
  round_daily_start integer NOT NULL DEFAULT 0,
  round_daily_end_unit integer NOT NULL DEFAULT 0,
  round_daily_end integer NOT NULL DEFAULT 0,
  round_daily_time_work integer NOT NULL DEFAULT 0,
  round_daily_work integer NOT NULL DEFAULT 0,
  round_daily_rest_start_unit integer NOT NULL DEFAULT 0,
  round_daily_rest_start integer NOT NULL DEFAULT 0,
  round_daily_rest_end_unit integer NOT NULL DEFAULT 0,
  round_daily_rest_end integer NOT NULL DEFAULT 0,
  round_daily_rest_time_unit integer NOT NULL DEFAULT 0,
  round_daily_rest_time integer NOT NULL DEFAULT 0,
  round_daily_late_unit integer NOT NULL DEFAULT 0,
  round_daily_late integer NOT NULL DEFAULT 0,
  round_daily_leave_early_unit integer NOT NULL DEFAULT 0,
  round_daily_leave_early integer NOT NULL DEFAULT 0,
  round_daily_private_time_start integer NOT NULL DEFAULT 0,
  round_daily_private_in integer NOT NULL DEFAULT 0,
  round_daily_private_time_end integer NOT NULL DEFAULT 0,
  round_daily_private_out integer NOT NULL DEFAULT 0,
  round_daily_public_time_start integer NOT NULL DEFAULT 0,
  round_daily_public_in integer NOT NULL DEFAULT 0,
  round_daily_public_time_end integer NOT NULL DEFAULT 0,
  round_daily_public_out integer NOT NULL DEFAULT 0,
  round_daily_decrease_time_unit integer NOT NULL DEFAULT 0,
  round_daily_decrease_time integer NOT NULL DEFAULT 0,
  round_daily_overtime_unit integer NOT NULL DEFAULT 0,
  round_daily_overtime integer NOT NULL DEFAULT 0,
  round_daily_short_unpaid_unit integer NOT NULL DEFAULT 0,
  round_daily_short_unpaid integer NOT NULL DEFAULT 0,
  round_monthly_work_unit integer NOT NULL DEFAULT 0,
  round_monthly_work integer NOT NULL DEFAULT 0,
  round_monthly_rest_unit integer NOT NULL DEFAULT 0,
  round_monthly_rest integer NOT NULL DEFAULT 0,
  round_monthly_late_unit integer NOT NULL DEFAULT 0,
  round_monthly_late integer NOT NULL DEFAULT 0,
  round_monthly_early_unit integer NOT NULL DEFAULT 0,
  round_monthly_early integer NOT NULL DEFAULT 0,
  round_monthly_private_time integer NOT NULL DEFAULT 0,
  round_monthly_private integer NOT NULL DEFAULT 0,
  round_monthly_public_time integer NOT NULL DEFAULT 0,
  round_monthly_public integer NOT NULL DEFAULT 0,
  round_monthly_decrease_time integer NOT NULL DEFAULT 0,
  round_monthly_decrease integer NOT NULL DEFAULT 0,
  round_monthly_overtime_unit integer NOT NULL DEFAULT 0,
  round_monthly_overtime integer NOT NULL DEFAULT 0,
  round_monthly_short_unpaid_unit integer NOT NULL DEFAULT 0,
  round_monthly_short_unpaid integer NOT NULL DEFAULT 0,
  start_week integer NOT NULL DEFAULT 0,
  start_month integer NOT NULL DEFAULT 0,
  start_year integer NOT NULL DEFAULT 0,
  general_work_time time without time zone NOT NULL,
  start_day_time time without time zone NOT NULL,
  late_early_full time without time zone NOT NULL,
  late_early_half time without time zone NOT NULL,
  transfer_ahead_limit_month integer NOT NULL DEFAULT 0,
  transfer_ahead_limit_date integer NOT NULL DEFAULT 0,
  transfer_later_limit_month integer NOT NULL DEFAULT 0,
  transfer_later_limit_date integer NOT NULL DEFAULT 0,
  sub_holiday_limit_month integer NOT NULL DEFAULT 0,
  sub_holiday_limit_date integer NOT NULL DEFAULT 0,
  transfer_exchange integer NOT NULL DEFAULT 0,
  sub_holiday_exchange integer NOT NULL DEFAULT 0,
  sub_holiday_all_norm time without time zone NOT NULL,
  sub_holiday_half_norm time without time zone NOT NULL,
  sixty_hour_function_flag integer NOT NULL DEFAULT 0,
  sixty_hour_alternative_flag integer NOT NULL DEFAULT 0,
  month_sixty_hour_surcharge integer NOT NULL DEFAULT 0,
  weekday_over integer NOT NULL DEFAULT 0,
  weekday_alternative integer NOT NULL DEFAULT 0,
  alternative_cancel integer NOT NULL DEFAULT 0,
  alternative_specific integer NOT NULL DEFAULT 0,
  alternative_legal integer NOT NULL DEFAULT 0,
  specific_holiday integer NOT NULL DEFAULT 0,
  legal_holiday integer NOT NULL DEFAULT 0,
  prospects_months character varying(30) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_time_setting_pkey PRIMARY KEY (tmm_time_setting_id)
)
;
COMMENT ON TABLE tmm_time_setting IS '勤怠設定マスタ';
COMMENT ON COLUMN tmm_time_setting.tmm_time_setting_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_time_setting.work_setting_code IS '勤怠設定コード';
COMMENT ON COLUMN tmm_time_setting.activate_date IS '有効日';
COMMENT ON COLUMN tmm_time_setting.work_setting_name IS '勤怠設定名称';
COMMENT ON COLUMN tmm_time_setting.work_setting_abbr IS '勤怠設定略称';
COMMENT ON COLUMN tmm_time_setting.cutoff_code IS '締日コード';
COMMENT ON COLUMN tmm_time_setting.time_management_flag IS '勤怠管理対象フラグ';
COMMENT ON COLUMN tmm_time_setting.daily_approval_flag IS '日々申請対象フラグ';
COMMENT ON COLUMN tmm_time_setting.before_overtime_flag IS '勤務前残業フラグ';
COMMENT ON COLUMN tmm_time_setting.specific_holiday_handling IS '所定休日取扱';
COMMENT ON COLUMN tmm_time_setting.portal_time_buttons IS 'ポータル出退勤ボタン表示';
COMMENT ON COLUMN tmm_time_setting.portal_rest_buttons IS 'ポータル休憩ボタン表示';
COMMENT ON COLUMN tmm_time_setting.use_scheduled_time IS '勤務予定時間表示';
COMMENT ON COLUMN tmm_time_setting.round_daily_start_unit IS '日出勤丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_start IS '日出勤丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_end_unit IS '日退勤丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_end IS '日退勤丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_time_work IS '日勤務時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_work IS '日勤務時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_rest_start_unit IS '日休憩入丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_rest_start IS '日休憩入丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_rest_end_unit IS '日休憩戻丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_rest_end IS '日休憩戻丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_rest_time_unit IS '日休憩時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_rest_time IS '日休憩時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_late_unit IS '日遅刻丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_late IS '日遅刻丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_leave_early_unit IS '日早退丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_leave_early IS '日早退丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_private_time_start IS '日私用外出入丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_private_in IS '日私用外出入丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_private_time_end IS '日私用外出戻丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_private_out IS '日私用外出戻丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_public_time_start IS '日公用外出入丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_public_in IS '日公用外出入丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_public_time_end IS '日公用外出戻丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_public_out IS '日公用外出戻丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_decrease_time_unit IS '日減額対象丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_decrease_time IS '日減額対象時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_overtime_unit IS '日残業時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_overtime IS '日残業時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_daily_short_unpaid_unit IS '日無給時短時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_daily_short_unpaid IS '日無給時短時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_work_unit IS '月勤務時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_work IS '月勤務時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_rest_unit IS '月休憩時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_rest IS '月休憩時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_late_unit IS '月遅刻丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_late IS '月遅刻時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_early_unit IS '月早退丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_early IS '月早退丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_private_time IS '月私用外出丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_private IS '月私用外出時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_public_time IS '月公用外出丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_public IS '月公用外出時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_decrease_time IS '月減額対象丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_decrease IS '月減額対象時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_overtime_unit IS '月残業時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_overtime IS '月残業時間丸め';
COMMENT ON COLUMN tmm_time_setting.round_monthly_short_unpaid_unit IS '月無給時短時間丸め単位';
COMMENT ON COLUMN tmm_time_setting.round_monthly_short_unpaid IS '月無給時短時間丸め';
COMMENT ON COLUMN tmm_time_setting.start_week IS '週の起算曜日';
COMMENT ON COLUMN tmm_time_setting.start_month IS '月の起算日';
COMMENT ON COLUMN tmm_time_setting.start_year IS '年の起算月';
COMMENT ON COLUMN tmm_time_setting.general_work_time IS '所定労働時間';
COMMENT ON COLUMN tmm_time_setting.start_day_time IS '一日の起算時刻';
COMMENT ON COLUMN tmm_time_setting.late_early_full IS '遅刻早退限度時間(全日)';
COMMENT ON COLUMN tmm_time_setting.late_early_half IS '遅刻早退限度時間(半日)';
COMMENT ON COLUMN tmm_time_setting.transfer_ahead_limit_month IS '振休取得期限月(休出前)';
COMMENT ON COLUMN tmm_time_setting.transfer_ahead_limit_date IS '振休取得期限日(休出前)';
COMMENT ON COLUMN tmm_time_setting.transfer_later_limit_month IS '振休取得期限月(休出後)';
COMMENT ON COLUMN tmm_time_setting.transfer_later_limit_date IS '振休取得期限日(休出後)';
COMMENT ON COLUMN tmm_time_setting.sub_holiday_limit_month IS '代休取得期限月';
COMMENT ON COLUMN tmm_time_setting.sub_holiday_limit_date IS '代休取得期限日';
COMMENT ON COLUMN tmm_time_setting.transfer_exchange IS '半休入替取得(振休)';
COMMENT ON COLUMN tmm_time_setting.sub_holiday_exchange IS '半休入替取得(代休)';
COMMENT ON COLUMN tmm_time_setting.sub_holiday_all_norm IS '代休基準時間(全休)';
COMMENT ON COLUMN tmm_time_setting.sub_holiday_half_norm IS '代休基準時間(半休)';
COMMENT ON COLUMN tmm_time_setting.sixty_hour_function_flag IS '60時間超割増機能';
COMMENT ON COLUMN tmm_time_setting.sixty_hour_alternative_flag IS '60時間超代替休暇';
COMMENT ON COLUMN tmm_time_setting.month_sixty_hour_surcharge IS '月60時間超割増';
COMMENT ON COLUMN tmm_time_setting.weekday_over IS '平日残業割増';
COMMENT ON COLUMN tmm_time_setting.weekday_alternative IS '代替休暇平日';
COMMENT ON COLUMN tmm_time_setting.alternative_cancel IS '代替休暇放棄';
COMMENT ON COLUMN tmm_time_setting.alternative_specific IS '代替休暇所定休日';
COMMENT ON COLUMN tmm_time_setting.alternative_legal IS '代替休暇法定休日';
COMMENT ON COLUMN tmm_time_setting.specific_holiday IS '所定休日割増率';
COMMENT ON COLUMN tmm_time_setting.legal_holiday IS '法定休日割増率';
COMMENT ON COLUMN tmm_time_setting.prospects_months IS '見込月';
COMMENT ON COLUMN tmm_time_setting.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_time_setting.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_time_setting.insert_date IS '登録日';
COMMENT ON COLUMN tmm_time_setting.insert_user IS '登録者';
COMMENT ON COLUMN tmm_time_setting.update_date IS '更新日';
COMMENT ON COLUMN tmm_time_setting.update_user IS '更新者';


CREATE TABLE tmm_limit_standard
(
  tmm_limit_standard_id bigint NOT NULL DEFAULT 0,
  work_setting_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  term character varying(50) NOT NULL DEFAULT ''::character varying,
  limit_time integer NOT NULL DEFAULT 0,
  attention_time integer NOT NULL DEFAULT 0,
  warning_time integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_limit_standard_pkey PRIMARY KEY (tmm_limit_standard_id)
)
;
COMMENT ON TABLE tmm_limit_standard IS '限度基準マスタ';
COMMENT ON COLUMN tmm_limit_standard.tmm_limit_standard_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_limit_standard.work_setting_code IS '勤怠設定コード';
COMMENT ON COLUMN tmm_limit_standard.activate_date IS '有効日';
COMMENT ON COLUMN tmm_limit_standard.term IS '期間';
COMMENT ON COLUMN tmm_limit_standard.limit_time IS '時間外限度時間';
COMMENT ON COLUMN tmm_limit_standard.attention_time IS '時間外注意時間';
COMMENT ON COLUMN tmm_limit_standard.warning_time IS '時間外警告時間';
COMMENT ON COLUMN tmm_limit_standard.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_limit_standard.insert_date IS '登録日';
COMMENT ON COLUMN tmm_limit_standard.insert_user IS '登録者';
COMMENT ON COLUMN tmm_limit_standard.update_date IS '更新日';
COMMENT ON COLUMN tmm_limit_standard.update_user IS '更新者';


CREATE TABLE tmm_work_type
(
  tmm_work_type_id bigint NOT NULL DEFAULT 0,
  work_type_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  work_type_name character varying(50) NOT NULL DEFAULT ''::character varying,
  work_type_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_work_type_pkey PRIMARY KEY (tmm_work_type_id)
)
;
COMMENT ON TABLE tmm_work_type IS '勤務形態マスタ';
COMMENT ON COLUMN tmm_work_type.tmm_work_type_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_work_type.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmm_work_type.activate_date IS '有効日';
COMMENT ON COLUMN tmm_work_type.work_type_name IS '勤務形態名称';
COMMENT ON COLUMN tmm_work_type.work_type_abbr IS '勤務形態略称';
COMMENT ON COLUMN tmm_work_type.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_work_type.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_work_type.insert_date IS '登録日';
COMMENT ON COLUMN tmm_work_type.insert_user IS '登録者';
COMMENT ON COLUMN tmm_work_type.update_date IS '更新日';
COMMENT ON COLUMN tmm_work_type.update_user IS '更新者';


CREATE TABLE tmm_work_type_item
(
  tmm_work_type_item_id bigint NOT NULL DEFAULT 0,
  work_type_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  work_type_item_code character varying(16) NOT NULL DEFAULT ''::character varying,
  work_type_item_value timestamp without time zone NOT NULL,
  preliminary character varying(64) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_work_type_item_pkey PRIMARY KEY (tmm_work_type_item_id)
)
;
COMMENT ON TABLE tmm_work_type_item IS '勤務形態項目マスタ';
COMMENT ON COLUMN tmm_work_type_item.tmm_work_type_item_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_work_type_item.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmm_work_type_item.activate_date IS '有効日';
COMMENT ON COLUMN tmm_work_type_item.work_type_item_code IS '勤務形態項目コード';
COMMENT ON COLUMN tmm_work_type_item.work_type_item_value IS '勤務形態項目値';
COMMENT ON COLUMN tmm_work_type_item.preliminary IS '勤務形態項目値(予備)';
COMMENT ON COLUMN tmm_work_type_item.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_work_type_item.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_work_type_item.insert_date IS '登録日';
COMMENT ON COLUMN tmm_work_type_item.insert_user IS '登録者';
COMMENT ON COLUMN tmm_work_type_item.update_date IS '更新日';
COMMENT ON COLUMN tmm_work_type_item.update_user IS '更新者';


CREATE TABLE tmm_work_type_pattern
(
  tmm_work_type_pattern_id bigint NOT NULL DEFAULT 0,
  pattern_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  pattern_name character varying(50) NOT NULL DEFAULT ''::character varying,
  pattern_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_work_type_pattern_pkey PRIMARY KEY (tmm_work_type_pattern_id)
)
;
COMMENT ON TABLE tmm_work_type_pattern IS '勤務形態パターンマスタ';
COMMENT ON COLUMN tmm_work_type_pattern.tmm_work_type_pattern_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_work_type_pattern.pattern_code IS 'パターンコード';
COMMENT ON COLUMN tmm_work_type_pattern.activate_date IS '有効日';
COMMENT ON COLUMN tmm_work_type_pattern.pattern_name IS 'パターン名称';
COMMENT ON COLUMN tmm_work_type_pattern.pattern_abbr IS 'パターン略称';
COMMENT ON COLUMN tmm_work_type_pattern.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_work_type_pattern.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_work_type_pattern.insert_date IS '登録日';
COMMENT ON COLUMN tmm_work_type_pattern.insert_user IS '登録者';
COMMENT ON COLUMN tmm_work_type_pattern.update_date IS '更新日';
COMMENT ON COLUMN tmm_work_type_pattern.update_user IS '更新者';


CREATE TABLE tma_work_type_pattern_item
(
  tma_work_type_pattern_item_id bigint NOT NULL DEFAULT 0,
  pattern_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  work_type_code character varying(10) NOT NULL DEFAULT ''::character varying,
  item_order integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tma_work_type_pattern_item_pkey PRIMARY KEY (tma_work_type_pattern_item_id)
)
;
COMMENT ON TABLE tma_work_type_pattern_item IS '勤務形態パターン項目マスタ';
COMMENT ON COLUMN tma_work_type_pattern_item.tma_work_type_pattern_item_id IS 'レコード識別ID';
COMMENT ON COLUMN tma_work_type_pattern_item.pattern_code IS 'パターンコード';
COMMENT ON COLUMN tma_work_type_pattern_item.activate_date IS '有効日';
COMMENT ON COLUMN tma_work_type_pattern_item.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tma_work_type_pattern_item.item_order IS '項目順序';
COMMENT ON COLUMN tma_work_type_pattern_item.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tma_work_type_pattern_item.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tma_work_type_pattern_item.insert_date IS '登録日';
COMMENT ON COLUMN tma_work_type_pattern_item.insert_user IS '登録者';
COMMENT ON COLUMN tma_work_type_pattern_item.update_date IS '更新日';
COMMENT ON COLUMN tma_work_type_pattern_item.update_user IS '更新者';


CREATE TABLE tmm_paid_holiday
(
  tmm_paid_holiday_id bigint NOT NULL DEFAULT 0,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  paid_holiday_name character varying(50) NOT NULL DEFAULT ''::character varying,
  paid_holiday_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  paid_holiday_type integer NOT NULL DEFAULT 0,
  work_ratio integer NOT NULL DEFAULT 0,
  schedule_giving integer NOT NULL DEFAULT 0,
  timely_paid_holiday_flag integer NOT NULL DEFAULT 0,
  timely_paid_holiday_time integer NOT NULL DEFAULT 1,
  time_acquisition_limit_days integer NOT NULL DEFAULT 0,
  time_acquisition_limit_times integer NOT NULL DEFAULT 0,
  appli_time_interval integer NOT NULL DEFAULT 0,
  max_carry_over_amount integer NOT NULL DEFAULT 0,
  total_max_amount integer NOT NULL DEFAULT 0,
  max_carry_over_year integer NOT NULL DEFAULT 0,
  max_carry_over_times integer NOT NULL DEFAULT 0,
  half_day_unit_flag integer NOT NULL DEFAULT 0,
  work_on_holiday_calc integer NOT NULL DEFAULT 0,
  point_date_month integer NOT NULL DEFAULT 0,
  point_date_day integer NOT NULL DEFAULT 0,
  general_point_amount integer NOT NULL DEFAULT 0,
  general_joining_month integer NOT NULL DEFAULT 0,
  general_joining_amount integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_paid_holiday_pkey PRIMARY KEY (tmm_paid_holiday_id)
)
;
COMMENT ON TABLE tmm_paid_holiday IS '有給休暇マスタ';
COMMENT ON COLUMN tmm_paid_holiday.tmm_paid_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_paid_holiday.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_paid_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmm_paid_holiday.paid_holiday_name IS '有休名称';
COMMENT ON COLUMN tmm_paid_holiday.paid_holiday_abbr IS '有休略称';
COMMENT ON COLUMN tmm_paid_holiday.paid_holiday_type IS '付与区分';
COMMENT ON COLUMN tmm_paid_holiday.work_ratio IS '出勤率';
COMMENT ON COLUMN tmm_paid_holiday.schedule_giving IS '仮付与日';
COMMENT ON COLUMN tmm_paid_holiday.timely_paid_holiday_flag IS '時間単位有休機能';
COMMENT ON COLUMN tmm_paid_holiday.timely_paid_holiday_time IS '有休単位時間';
COMMENT ON COLUMN tmm_paid_holiday.time_acquisition_limit_days IS '有休時間取得限度日数';
COMMENT ON COLUMN tmm_paid_holiday.time_acquisition_limit_times IS '有休時間取得限度時間';
COMMENT ON COLUMN tmm_paid_holiday.appli_time_interval IS '申請時間間隔';
COMMENT ON COLUMN tmm_paid_holiday.max_carry_over_amount IS '有休繰越';
COMMENT ON COLUMN tmm_paid_holiday.total_max_amount IS '合計最大保有日数';
COMMENT ON COLUMN tmm_paid_holiday.max_carry_over_year IS '最大繰越年数';
COMMENT ON COLUMN tmm_paid_holiday.max_carry_over_times IS '時間単位繰越';
COMMENT ON COLUMN tmm_paid_holiday.half_day_unit_flag IS '半日単位取得';
COMMENT ON COLUMN tmm_paid_holiday.work_on_holiday_calc IS '休日出勤取扱';
COMMENT ON COLUMN tmm_paid_holiday.point_date_month IS '基準日(月)';
COMMENT ON COLUMN tmm_paid_holiday.point_date_day IS '基準日(日)';
COMMENT ON COLUMN tmm_paid_holiday.general_point_amount IS '登録情報超過後(基準日)';
COMMENT ON COLUMN tmm_paid_holiday.general_joining_month IS '登録情報超過後(月)';
COMMENT ON COLUMN tmm_paid_holiday.general_joining_amount IS '登録情報超過後(日)';
COMMENT ON COLUMN tmm_paid_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_paid_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_paid_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmm_paid_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmm_paid_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmm_paid_holiday.update_user IS '更新者';


CREATE TABLE tmm_paid_holiday_first_year
(
  tmm_paid_holiday_first_year_id bigint NOT NULL DEFAULT 0,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  entrance_month integer NOT NULL DEFAULT 0,
  giving_month integer NOT NULL DEFAULT 0,
  giving_amount integer NOT NULL DEFAULT 0,
  giving_limit integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_paid_holiday_first_year_pkey PRIMARY KEY (tmm_paid_holiday_first_year_id)
)
;
COMMENT ON TABLE tmm_paid_holiday_first_year IS '有給休暇初年度マスタ';
COMMENT ON COLUMN tmm_paid_holiday_first_year.tmm_paid_holiday_first_year_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_paid_holiday_first_year.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_paid_holiday_first_year.activate_date IS '有効日';
COMMENT ON COLUMN tmm_paid_holiday_first_year.entrance_month IS '入社月';
COMMENT ON COLUMN tmm_paid_holiday_first_year.giving_month IS '付与月';
COMMENT ON COLUMN tmm_paid_holiday_first_year.giving_amount IS '付与日数';
COMMENT ON COLUMN tmm_paid_holiday_first_year.giving_limit IS '利用期限';
COMMENT ON COLUMN tmm_paid_holiday_first_year.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_paid_holiday_first_year.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_paid_holiday_first_year.insert_date IS '登録日';
COMMENT ON COLUMN tmm_paid_holiday_first_year.insert_user IS '登録者';
COMMENT ON COLUMN tmm_paid_holiday_first_year.update_date IS '更新日';
COMMENT ON COLUMN tmm_paid_holiday_first_year.update_user IS '更新者';


CREATE TABLE tmm_paid_holiday_point_date
(
  tmm_paid_holiday_point_date_id bigint NOT NULL DEFAULT 0,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  times_point_date integer NOT NULL DEFAULT 0,
  point_date_amount integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_paid_holiday_point_date_pkey PRIMARY KEY (tmm_paid_holiday_point_date_id)
)
;
COMMENT ON TABLE tmm_paid_holiday_point_date IS '有給休暇基準日マスタ';
COMMENT ON COLUMN tmm_paid_holiday_point_date.tmm_paid_holiday_point_date_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_paid_holiday_point_date.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_paid_holiday_point_date.activate_date IS '有効日';
COMMENT ON COLUMN tmm_paid_holiday_point_date.times_point_date IS '基準日経過回数';
COMMENT ON COLUMN tmm_paid_holiday_point_date.point_date_amount IS '付与日数';
COMMENT ON COLUMN tmm_paid_holiday_point_date.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_paid_holiday_point_date.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_paid_holiday_point_date.insert_date IS '登録日';
COMMENT ON COLUMN tmm_paid_holiday_point_date.insert_user IS '登録者';
COMMENT ON COLUMN tmm_paid_holiday_point_date.update_date IS '更新日';
COMMENT ON COLUMN tmm_paid_holiday_point_date.update_user IS '更新者';


CREATE TABLE tmm_paid_holiday_entrance_date
(
  tmm_paid_holiday_entrance_date_id bigint NOT NULL DEFAULT 0,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  work_month integer NOT NULL DEFAULT 0,
  joining_date_amount integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_paid_holiday_entrance_date_pkey PRIMARY KEY (tmm_paid_holiday_entrance_date_id)
)
;
COMMENT ON TABLE tmm_paid_holiday_entrance_date IS '有給休暇入社日マスタ';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.tmm_paid_holiday_entrance_date_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.activate_date IS '有効日';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.work_month IS '勤続勤務月数';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.joining_date_amount IS '付与日数';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.insert_date IS '登録日';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.insert_user IS '登録者';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.update_date IS '更新日';
COMMENT ON COLUMN tmm_paid_holiday_entrance_date.update_user IS '更新者';


CREATE TABLE tmm_stock_holiday
(
  tmm_stock_holiday_id bigint NOT NULL DEFAULT 0,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  stock_year_amount integer NOT NULL DEFAULT 0,
  stock_total_amount integer NOT NULL DEFAULT 0,
  stock_limit_date integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_stock_holiday_pkey PRIMARY KEY (tmm_stock_holiday_id)
)
;
COMMENT ON TABLE tmm_stock_holiday IS 'ストック休暇マスタ';
COMMENT ON COLUMN tmm_stock_holiday.tmm_stock_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_stock_holiday.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_stock_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmm_stock_holiday.stock_year_amount IS '最大年間積立日数';
COMMENT ON COLUMN tmm_stock_holiday.stock_total_amount IS '最大合計積立日数';
COMMENT ON COLUMN tmm_stock_holiday.stock_limit_date IS '有効期限';
COMMENT ON COLUMN tmm_stock_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_stock_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_stock_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmm_stock_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmm_stock_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmm_stock_holiday.update_user IS '更新者';


CREATE TABLE tmm_holiday
(
  tmm_holiday_id bigint NOT NULL DEFAULT 0,
  holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  holiday_type integer NOT NULL DEFAULT 0,
  holiday_name character varying(50) NOT NULL DEFAULT ''::character varying,
  holiday_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  holiday_giving double precision NOT NULL DEFAULT 0,
  no_limit integer NOT NULL DEFAULT 0,
  holiday_limit_month integer NOT NULL DEFAULT 0,
  holiday_limit_day integer NOT NULL DEFAULT 0,
  half_holiday_request integer NOT NULL DEFAULT 0,
  continuous_acquisition integer NOT NULL DEFAULT 0,
  timely_holiday_flag integer NOT NULL DEFAULT 0,
  paid_holiday_calc integer NOT NULL DEFAULT 0,
  salary integer NOT NULL DEFAULT 0,
  reason_type integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_holiday_pkey PRIMARY KEY (tmm_holiday_id)
)
;
COMMENT ON TABLE tmm_holiday IS '休暇種別マスタ';
COMMENT ON COLUMN tmm_holiday.tmm_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_holiday.holiday_code IS '休暇コード';
COMMENT ON COLUMN tmm_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmm_holiday.holiday_type IS '休暇区分';
COMMENT ON COLUMN tmm_holiday.holiday_name IS '休暇名称';
COMMENT ON COLUMN tmm_holiday.holiday_abbr IS '休暇略称';
COMMENT ON COLUMN tmm_holiday.holiday_giving IS '標準付与日数';
COMMENT ON COLUMN tmm_holiday.no_limit IS '付与日数無制限';
COMMENT ON COLUMN tmm_holiday.holiday_limit_month IS '取得期限(月)';
COMMENT ON COLUMN tmm_holiday.holiday_limit_day IS '取得期限(日)';
COMMENT ON COLUMN tmm_holiday.half_holiday_request IS '半休申請';
COMMENT ON COLUMN tmm_holiday.continuous_acquisition IS '連続取得';
COMMENT ON COLUMN tmm_holiday.timely_holiday_flag IS '時間単位取得';
COMMENT ON COLUMN tmm_holiday.paid_holiday_calc IS '出勤率計算';
COMMENT ON COLUMN tmm_holiday.salary IS '有給/無給';
COMMENT ON COLUMN tmm_holiday.reason_type IS '理由種別';
COMMENT ON COLUMN tmm_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmm_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmm_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmm_holiday.update_user IS '更新者';


CREATE TABLE tmd_holiday
(
  tmd_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  holiday_type integer NOT NULL DEFAULT 0,
  giving_day double precision NOT NULL DEFAULT 0,
  giving_hour integer NOT NULL DEFAULT 0,
  cancel_day double precision NOT NULL DEFAULT 0,
  cancel_hour integer NOT NULL DEFAULT 0,
  holiday_limit_date date NOT NULL,
  holiday_limit_month integer NOT NULL DEFAULT 0,
  holiday_limit_day integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_holiday_pkey PRIMARY KEY (tmd_holiday_id)
)
;
COMMENT ON TABLE tmd_holiday IS '休暇データ';
COMMENT ON COLUMN tmd_holiday.tmd_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmd_holiday.holiday_code IS '休暇コード';
COMMENT ON COLUMN tmd_holiday.holiday_type IS '休暇区分';
COMMENT ON COLUMN tmd_holiday.giving_day IS '付与日数';
COMMENT ON COLUMN tmd_holiday.giving_hour IS '付与時間数';
COMMENT ON COLUMN tmd_holiday.cancel_day IS '廃棄日数';
COMMENT ON COLUMN tmd_holiday.cancel_hour IS '廃棄時間数';
COMMENT ON COLUMN tmd_holiday.holiday_limit_date IS '取得期限';
COMMENT ON COLUMN tmd_holiday.holiday_limit_month IS '取得期限(月)';
COMMENT ON COLUMN tmd_holiday.holiday_limit_day IS '取得期限(日)';
COMMENT ON COLUMN tmd_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmd_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmd_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmd_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmd_holiday.update_user IS '更新者';


CREATE TABLE tmd_paid_holiday
(
  tmd_paid_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  acquisition_date date NOT NULL,
  limit_date date NOT NULL,
  hold_day double precision NOT NULL DEFAULT 0,
  hold_hour integer NOT NULL DEFAULT 0,
  giving_day double precision NOT NULL DEFAULT 0,
  giving_hour integer NOT NULL DEFAULT 0,
  cancel_day double precision NOT NULL DEFAULT 0,
  cancel_hour integer NOT NULL DEFAULT 0,
  use_day double precision NOT NULL DEFAULT 0,
  use_hour integer NOT NULL DEFAULT 0,
  denominator_day_hour integer NOT NULL DEFAULT 0,
  temporary_flag integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_paid_holiday_pkey PRIMARY KEY (tmd_paid_holiday_id)
)
;
COMMENT ON TABLE tmd_paid_holiday IS '有給休暇データ';
COMMENT ON COLUMN tmd_paid_holiday.tmd_paid_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_paid_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_paid_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmd_paid_holiday.acquisition_date IS '取得日';
COMMENT ON COLUMN tmd_paid_holiday.limit_date IS '期限日';
COMMENT ON COLUMN tmd_paid_holiday.hold_day IS '保有日数';
COMMENT ON COLUMN tmd_paid_holiday.hold_hour IS '保有時間数';
COMMENT ON COLUMN tmd_paid_holiday.giving_day IS '付与日数';
COMMENT ON COLUMN tmd_paid_holiday.giving_hour IS '付与時間数';
COMMENT ON COLUMN tmd_paid_holiday.cancel_day IS '廃棄日数';
COMMENT ON COLUMN tmd_paid_holiday.cancel_hour IS '廃棄時間数';
COMMENT ON COLUMN tmd_paid_holiday.use_day IS '使用日数';
COMMENT ON COLUMN tmd_paid_holiday.use_hour IS '使用時間数';
COMMENT ON COLUMN tmd_paid_holiday.denominator_day_hour IS '時間変換日分母';
COMMENT ON COLUMN tmd_paid_holiday.temporary_flag IS '仮付与フラグ';
COMMENT ON COLUMN tmd_paid_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmd_paid_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_paid_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmd_paid_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmd_paid_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmd_paid_holiday.update_user IS '更新者';


CREATE TABLE tmt_paid_holiday
(
  tmt_paid_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  acquisition_date date NOT NULL,
  giving_day double precision NOT NULL DEFAULT 0,
  giving_hour integer NOT NULL DEFAULT 0,
  cancel_day double precision NOT NULL DEFAULT 0,
  cancel_hour integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmt_paid_holiday_pkey PRIMARY KEY (tmt_paid_holiday_id)
)
;
COMMENT ON TABLE tmt_paid_holiday IS '有給休暇トランザクション';
COMMENT ON COLUMN tmt_paid_holiday.tmt_paid_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmt_paid_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN tmt_paid_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmt_paid_holiday.acquisition_date IS '取得日';
COMMENT ON COLUMN tmt_paid_holiday.giving_day IS '付与日数';
COMMENT ON COLUMN tmt_paid_holiday.giving_hour IS '付与時間数';
COMMENT ON COLUMN tmt_paid_holiday.cancel_day IS '廃棄日数';
COMMENT ON COLUMN tmt_paid_holiday.cancel_hour IS '廃棄時間数';
COMMENT ON COLUMN tmt_paid_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmt_paid_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmt_paid_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmt_paid_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmt_paid_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmt_paid_holiday.update_user IS '更新者';


CREATE TABLE tmd_stock_holiday
(
  tmd_stock_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  acquisition_date date NOT NULL,
  limit_date date NOT NULL,
  hold_day double precision NOT NULL DEFAULT 0,
  giving_day double precision NOT NULL DEFAULT 0,
  cancel_day double precision NOT NULL DEFAULT 0,
  use_day double precision NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmd_stock_holiday_pkey PRIMARY KEY (tmd_stock_holiday_id)
)
;
COMMENT ON TABLE tmd_stock_holiday IS 'ストック休暇データ';
COMMENT ON COLUMN tmd_stock_holiday.tmd_stock_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmd_stock_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN tmd_stock_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmd_stock_holiday.acquisition_date IS '取得日';
COMMENT ON COLUMN tmd_stock_holiday.limit_date IS '期限日';
COMMENT ON COLUMN tmd_stock_holiday.hold_day IS '保有日数';
COMMENT ON COLUMN tmd_stock_holiday.giving_day IS '付与日数';
COMMENT ON COLUMN tmd_stock_holiday.cancel_day IS '廃棄日数';
COMMENT ON COLUMN tmd_stock_holiday.use_day IS '使用日数';
COMMENT ON COLUMN tmd_stock_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmd_stock_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmd_stock_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmd_stock_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmd_stock_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmd_stock_holiday.update_user IS '更新者';


CREATE TABLE tmt_stock_holiday
(
  tmt_stock_holiday_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  acquisition_date date NOT NULL,
  giving_day double precision NOT NULL DEFAULT 0,
  cancel_day double precision NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmt_stock_holiday_pkey PRIMARY KEY (tmt_stock_holiday_id)
)
;
COMMENT ON TABLE tmt_stock_holiday IS 'ストック休暇トランザクション';
COMMENT ON COLUMN tmt_stock_holiday.tmt_stock_holiday_id IS 'レコード識別ID';
COMMENT ON COLUMN tmt_stock_holiday.personal_id IS '個人ID';
COMMENT ON COLUMN tmt_stock_holiday.activate_date IS '有効日';
COMMENT ON COLUMN tmt_stock_holiday.acquisition_date IS '取得日';
COMMENT ON COLUMN tmt_stock_holiday.giving_day IS '付与日数';
COMMENT ON COLUMN tmt_stock_holiday.cancel_day IS '廃棄日数';
COMMENT ON COLUMN tmt_stock_holiday.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmt_stock_holiday.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmt_stock_holiday.insert_date IS '登録日';
COMMENT ON COLUMN tmt_stock_holiday.insert_user IS '登録者';
COMMENT ON COLUMN tmt_stock_holiday.update_date IS '更新日';
COMMENT ON COLUMN tmt_stock_holiday.update_user IS '更新者';


CREATE TABLE tmm_schedule
(
  tmm_schedule_id bigint NOT NULL DEFAULT 0,
  schedule_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  schedule_name character varying(50) NOT NULL DEFAULT ''::character varying,
  schedule_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  fiscal_year integer NOT NULL DEFAULT 0,
  pattern_code character varying(10) NOT NULL DEFAULT ''::character varying,
  work_type_change_flag integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_schedule_pkey PRIMARY KEY (tmm_schedule_id)
)
;
COMMENT ON TABLE tmm_schedule IS 'カレンダマスタ';
COMMENT ON COLUMN tmm_schedule.tmm_schedule_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_schedule.schedule_code IS 'カレンダコード';
COMMENT ON COLUMN tmm_schedule.activate_date IS '有効日';
COMMENT ON COLUMN tmm_schedule.schedule_name IS 'カレンダ名称';
COMMENT ON COLUMN tmm_schedule.schedule_abbr IS 'カレンダ略称';
COMMENT ON COLUMN tmm_schedule.fiscal_year IS '年度';
COMMENT ON COLUMN tmm_schedule.pattern_code IS 'パターンコード';
COMMENT ON COLUMN tmm_schedule.work_type_change_flag IS '勤務形態変更フラグ';
COMMENT ON COLUMN tmm_schedule.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_schedule.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_schedule.insert_date IS '登録日';
COMMENT ON COLUMN tmm_schedule.insert_user IS '登録者';
COMMENT ON COLUMN tmm_schedule.update_date IS '更新日';
COMMENT ON COLUMN tmm_schedule.update_user IS '更新者';


CREATE TABLE tmm_schedule_date
(
  tmm_schedule_date_id bigint NOT NULL DEFAULT 0,
  schedule_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  schedule_date date NOT NULL,
  works integer NOT NULL DEFAULT 0,
  work_type_code character varying(20) NOT NULL DEFAULT ''::character varying,
  remark character varying(50) NOT NULL DEFAULT ''::character varying,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_schedule_date_pkey PRIMARY KEY (tmm_schedule_date_id)
)
;
COMMENT ON TABLE tmm_schedule_date IS 'カレンダ日マスタ';
COMMENT ON COLUMN tmm_schedule_date.tmm_schedule_date_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_schedule_date.schedule_code IS 'カレンダコード';
COMMENT ON COLUMN tmm_schedule_date.activate_date IS '有効日';
COMMENT ON COLUMN tmm_schedule_date.schedule_date IS '日';
COMMENT ON COLUMN tmm_schedule_date.works IS '勤務回数';
COMMENT ON COLUMN tmm_schedule_date.work_type_code IS '勤務形態コード';
COMMENT ON COLUMN tmm_schedule_date.remark IS '備考';
COMMENT ON COLUMN tmm_schedule_date.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_schedule_date.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_schedule_date.insert_date IS '登録日';
COMMENT ON COLUMN tmm_schedule_date.insert_user IS '登録者';
COMMENT ON COLUMN tmm_schedule_date.update_date IS '更新日';
COMMENT ON COLUMN tmm_schedule_date.update_user IS '更新者';


CREATE TABLE tmm_cutoff
(
  tmm_cutoff_id bigint NOT NULL DEFAULT 0,
  cutoff_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  cutoff_name character varying(50) NOT NULL DEFAULT ''::character varying,
  cutoff_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  cutoff_date integer NOT NULL DEFAULT 0,
  cutoff_type character varying(50) NOT NULL DEFAULT ''::character varying,
  no_approval integer NOT NULL DEFAULT 0,
  self_tightening integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_cutoff_pkey PRIMARY KEY (tmm_cutoff_id)
)
;
COMMENT ON TABLE tmm_cutoff IS '締日マスタ';
COMMENT ON COLUMN tmm_cutoff.tmm_cutoff_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_cutoff.cutoff_code IS '締日コード';
COMMENT ON COLUMN tmm_cutoff.activate_date IS '有効日';
COMMENT ON COLUMN tmm_cutoff.cutoff_name IS '締日名称';
COMMENT ON COLUMN tmm_cutoff.cutoff_abbr IS '締日略称';
COMMENT ON COLUMN tmm_cutoff.cutoff_date IS '締日';
COMMENT ON COLUMN tmm_cutoff.cutoff_type IS '締区分';
COMMENT ON COLUMN tmm_cutoff.no_approval IS '未承認仮締';
COMMENT ON COLUMN tmm_cutoff.self_tightening IS '自己月締';
COMMENT ON COLUMN tmm_cutoff.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_cutoff.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_cutoff.insert_date IS '登録日';
COMMENT ON COLUMN tmm_cutoff.insert_user IS '登録者';
COMMENT ON COLUMN tmm_cutoff.update_date IS '更新日';
COMMENT ON COLUMN tmm_cutoff.update_user IS '更新者';


CREATE TABLE tmm_application
(
  tmm_application_id bigint NOT NULL DEFAULT 0,
  application_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  application_type integer NOT NULL DEFAULT 0,
  application_name character varying(50) NOT NULL DEFAULT ''::character varying,
  application_abbr character varying(6) NOT NULL DEFAULT ''::character varying,
  work_setting_code character varying(10) NOT NULL DEFAULT ''::character varying,
  schedule_code character varying(10) NOT NULL DEFAULT ''::character varying,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
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
  CONSTRAINT tmm_application_pkey PRIMARY KEY (tmm_application_id)
)
;
COMMENT ON TABLE tmm_application IS '設定適用マスタ';
COMMENT ON COLUMN tmm_application.tmm_application_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_application.application_code IS '設定適用コード';
COMMENT ON COLUMN tmm_application.activate_date IS '有効日';
COMMENT ON COLUMN tmm_application.application_type IS '設定適用区分';
COMMENT ON COLUMN tmm_application.application_name IS '設定適用名称';
COMMENT ON COLUMN tmm_application.application_abbr IS '設定適用略称';
COMMENT ON COLUMN tmm_application.work_setting_code IS '勤怠設定コード';
COMMENT ON COLUMN tmm_application.schedule_code IS 'カレンダコード';
COMMENT ON COLUMN tmm_application.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_application.work_place_code IS '勤務地コード';
COMMENT ON COLUMN tmm_application.employment_contract_code IS '雇用契約コード';
COMMENT ON COLUMN tmm_application.section_code IS '所属コード';
COMMENT ON COLUMN tmm_application.position_code IS '職位コード';
COMMENT ON COLUMN tmm_application.personal_ids IS '個人ID';
COMMENT ON COLUMN tmm_application.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_application.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_application.insert_date IS '登録日';
COMMENT ON COLUMN tmm_application.insert_user IS '登録者';
COMMENT ON COLUMN tmm_application.update_date IS '更新日';
COMMENT ON COLUMN tmm_application.update_user IS '更新者';


CREATE TABLE tmt_attendance
(
  tmt_attendance_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  work_date date NOT NULL,
  attendance_type character varying(20) NOT NULL DEFAULT ''::character varying,
  numerator integer NOT NULL DEFAULT 0,
  denominator integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmt_attendance_pkey PRIMARY KEY (tmt_attendance_id)
)
;
COMMENT ON TABLE tmt_attendance IS '勤怠トランザクション';
COMMENT ON COLUMN tmt_attendance.tmt_attendance_id IS 'レコード識別ID';
COMMENT ON COLUMN tmt_attendance.personal_id IS '個人ID';
COMMENT ON COLUMN tmt_attendance.work_date IS '勤務日';
COMMENT ON COLUMN tmt_attendance.attendance_type IS '出勤区分';
COMMENT ON COLUMN tmt_attendance.numerator IS '出勤率算定分子';
COMMENT ON COLUMN tmt_attendance.denominator IS '出勤率算定分母';
COMMENT ON COLUMN tmt_attendance.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmt_attendance.insert_date IS '登録日';
COMMENT ON COLUMN tmt_attendance.insert_user IS '登録者';
COMMENT ON COLUMN tmt_attendance.update_date IS '更新日';
COMMENT ON COLUMN tmt_attendance.update_user IS '更新者';


CREATE TABLE tmm_paid_holiday_proportionally
(
  tmm_paid_holiday_proportionally_id bigint NOT NULL DEFAULT 0,
  paid_holiday_code character varying(10) NOT NULL DEFAULT ''::character varying,
  activate_date date NOT NULL,
  prescribed_weekly_working_days integer NOT NULL DEFAULT 0,
  continuous_service_terms_counting_from_the_employment_day integer NOT NULL DEFAULT 0,
  days integer NOT NULL DEFAULT 0,
  inactivate_flag integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmm_paid_holiday_proportionally_pkey PRIMARY KEY (tmm_paid_holiday_proportionally_id)
)
;
COMMENT ON TABLE tmm_paid_holiday_proportionally IS '有給休暇比例付与マスタ';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.tmm_paid_holiday_proportionally_id IS 'レコード識別ID';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.paid_holiday_code IS '有休コード';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.activate_date IS '有効日';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.prescribed_weekly_working_days IS '週所定労働日数';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.continuous_service_terms_counting_from_the_employment_day IS '雇入れの日から起算した継続勤務期間';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.days IS '日数';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.inactivate_flag IS '無効フラグ';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.insert_date IS '登録日';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.insert_user IS '登録者';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.update_date IS '更新日';
COMMENT ON COLUMN tmm_paid_holiday_proportionally.update_user IS '更新者';


CREATE TABLE tmt_paid_holiday_grant
(
  tmt_paid_holiday_grant_id bigint NOT NULL DEFAULT 0,
  personal_id character varying(10) NOT NULL DEFAULT ''::character varying,
  grant_date date NOT NULL,
  grant_status integer NOT NULL DEFAULT 0,
  delete_flag integer NOT NULL DEFAULT 0,
  insert_date timestamp without time zone NOT NULL,
  insert_user character varying(50) NOT NULL DEFAULT ''::character varying,
  update_date timestamp without time zone NOT NULL,
  update_user character varying(50) NOT NULL DEFAULT ''::character varying,
  CONSTRAINT tmt_paid_holiday_grant_pkey PRIMARY KEY (tmt_paid_holiday_grant_id)
)
;
COMMENT ON TABLE tmt_paid_holiday_grant IS '有給休暇付与';
COMMENT ON COLUMN tmt_paid_holiday_grant.tmt_paid_holiday_grant_id IS 'レコード識別ID';
COMMENT ON COLUMN tmt_paid_holiday_grant.personal_id IS '個人ID';
COMMENT ON COLUMN tmt_paid_holiday_grant.grant_date IS '付与日';
COMMENT ON COLUMN tmt_paid_holiday_grant.grant_status IS '付与状態';
COMMENT ON COLUMN tmt_paid_holiday_grant.delete_flag IS '削除フラグ';
COMMENT ON COLUMN tmt_paid_holiday_grant.insert_date IS '登録日';
COMMENT ON COLUMN tmt_paid_holiday_grant.insert_user IS '登録者';
COMMENT ON COLUMN tmt_paid_holiday_grant.update_date IS '更新日';
COMMENT ON COLUMN tmt_paid_holiday_grant.update_user IS '更新者';


CREATE SEQUENCE tmd_attendance_id_seq;
CREATE SEQUENCE tmd_attendance_correction_id_seq;
CREATE SEQUENCE tmd_allowance_id_seq;
CREATE SEQUENCE tmd_rest_id_seq;
CREATE SEQUENCE tmd_go_out_id_seq;
CREATE SEQUENCE tmd_time_record_id_seq;
CREATE SEQUENCE tmd_total_time_id_seq;
CREATE SEQUENCE tmd_total_time_correction_id_seq;
CREATE SEQUENCE tmd_total_leave_id_seq;
CREATE SEQUENCE tmd_total_other_vacation_id_seq;
CREATE SEQUENCE tmd_total_absence_id_seq;
CREATE SEQUENCE tmd_total_allowance_id_seq;
CREATE SEQUENCE tmt_total_time_id_seq;
CREATE SEQUENCE tmt_total_time_employee_id_seq;
CREATE SEQUENCE tmd_overtime_request_id_seq;
CREATE SEQUENCE tmd_holiday_request_id_seq;
CREATE SEQUENCE tmd_work_on_holiday_request_id_seq;
CREATE SEQUENCE tmd_sub_holiday_request_id_seq;
CREATE SEQUENCE tmd_work_type_change_request_id_seq;
CREATE SEQUENCE tmd_difference_request_id_seq;
CREATE SEQUENCE tmd_substitute_id_seq;
CREATE SEQUENCE tmd_sub_holiday_id_seq;
CREATE SEQUENCE tmm_time_setting_id_seq;
CREATE SEQUENCE tmm_limit_standard_id_seq;
CREATE SEQUENCE tmm_work_type_id_seq;
CREATE SEQUENCE tmm_work_type_item_id_seq;
CREATE SEQUENCE tmm_work_type_pattern_id_seq;
CREATE SEQUENCE tma_work_type_pattern_item_id_seq;
CREATE SEQUENCE tmm_paid_holiday_id_seq;
CREATE SEQUENCE tmm_paid_holiday_first_year_id_seq;
CREATE SEQUENCE tmm_paid_holiday_point_date_id_seq;
CREATE SEQUENCE tmm_paid_holiday_entrance_date_id_seq;
CREATE SEQUENCE tmm_stock_holiday_id_seq;
CREATE SEQUENCE tmm_holiday_id_seq;
CREATE SEQUENCE tmd_holiday_id_seq;
CREATE SEQUENCE tmd_paid_holiday_id_seq;
CREATE SEQUENCE tmt_paid_holiday_id_seq;
CREATE SEQUENCE tmd_stock_holiday_id_seq;
CREATE SEQUENCE tmt_stock_holiday_id_seq;
CREATE SEQUENCE tmm_schedule_id_seq;
CREATE SEQUENCE tmm_schedule_date_id_seq;
CREATE SEQUENCE tmm_cutoff_id_seq;
CREATE SEQUENCE tmm_application_id_seq;
CREATE SEQUENCE tmt_attendance_id_seq;
CREATE SEQUENCE tmm_paid_holiday_proportionally_id_seq;
CREATE SEQUENCE tmt_paid_holiday_grant_id_seq;

CREATE INDEX tma_work_type_pattern_item_index1 ON tma_work_type_pattern_item(pattern_code, activate_date);
CREATE INDEX tmd_allowance_index1 ON tmd_allowance(personal_id, work_date);
CREATE INDEX tmd_attendance_index1 ON tmd_attendance(personal_id, work_date);
CREATE INDEX tmd_attendance_index2 ON tmd_attendance(workflow);
CREATE INDEX tmd_attendance_correction_index1 ON tmd_attendance_correction(personal_id, work_date);
CREATE INDEX tmd_difference_request_index1 ON tmd_difference_request(personal_id, request_date);
CREATE INDEX tmd_go_out_index1 ON tmd_go_out(personal_id, work_date);
CREATE INDEX tmd_holiday_index1 ON tmd_holiday(personal_id, activate_date);
CREATE INDEX tmd_holiday_request_index1 ON tmd_holiday_request(personal_id, request_start_date, request_end_date);
CREATE INDEX tmd_overtime_request_index1 ON tmd_overtime_request(personal_id, request_date);
CREATE INDEX tmd_paid_holiday_index1 ON tmd_paid_holiday(personal_id, activate_date, acquisition_date);
CREATE INDEX tmd_rest_index1 ON tmd_rest(personal_id, work_date);
CREATE INDEX tmd_stock_holiday_index1 ON tmd_stock_holiday(personal_id, activate_date, acquisition_date);
CREATE INDEX tmd_sub_holiday_index1 ON tmd_sub_holiday(personal_id, work_date);
CREATE INDEX tmd_sub_holiday_request_index1 ON tmd_sub_holiday_request(personal_id, request_date);
CREATE INDEX tmd_substitute_index1 ON tmd_substitute(personal_id, substitute_date);
CREATE INDEX tmd_time_record_index1 ON tmd_time_record(personal_id, work_date);
CREATE INDEX tmd_total_absence_index1 ON tmd_total_absence(personal_id, calculation_year, calculation_month);
CREATE INDEX tmd_total_allowance_index1 ON tmd_total_allowance(personal_id, calculation_year, calculation_month);
CREATE INDEX tmd_total_leave_index1 ON tmd_total_leave(personal_id, calculation_year, calculation_month);
CREATE INDEX tmd_total_other_vacation_index1 ON tmd_total_other_vacation(personal_id, calculation_year, calculation_month);
CREATE INDEX tmd_total_time_index1 ON tmd_total_time(personal_id, calculation_year, calculation_month);
CREATE INDEX tmd_total_time_correction_index1 ON tmd_total_time_correction(personal_id, calculation_year, calculation_month);
CREATE INDEX tmd_work_on_holiday_request_index1 ON tmd_work_on_holiday_request(personal_id, request_date);
CREATE INDEX tmd_work_type_change_request_index1 ON tmd_work_type_change_request(personal_id, request_date);
CREATE INDEX tmm_application_index1 ON tmm_application(application_code, activate_date);
CREATE INDEX tmm_cutoff_index1 ON tmm_cutoff(cutoff_code, activate_date);
CREATE INDEX tmm_holiday_index1 ON tmm_holiday(holiday_code, activate_date);
CREATE INDEX tmm_limit_standard_index1 ON tmm_limit_standard(work_setting_code, activate_date);
CREATE INDEX tmm_paid_holiday_index1 ON tmm_paid_holiday(paid_holiday_code, activate_date);
CREATE INDEX tmm_paid_holiday_entrance_date_index1 ON tmm_paid_holiday_entrance_date(paid_holiday_code, activate_date);
CREATE INDEX tmm_paid_holiday_first_year_index1 ON tmm_paid_holiday_first_year(paid_holiday_code, activate_date);
CREATE INDEX tmm_paid_holiday_point_date_index1 ON tmm_paid_holiday_point_date(paid_holiday_code, activate_date);
CREATE INDEX tmm_schedule_index1 ON tmm_schedule(schedule_code, activate_date);
CREATE INDEX tmm_schedule_date_index1 ON tmm_schedule_date(schedule_code, activate_date, schedule_date);
CREATE INDEX tmm_stock_holiday_index1 ON tmm_stock_holiday(paid_holiday_code, activate_date);
CREATE INDEX tmm_time_setting_index1 ON tmm_time_setting(work_setting_code, activate_date);
CREATE INDEX tmm_work_type_index1 ON tmm_work_type(work_type_code, activate_date);
CREATE INDEX tmm_work_type_item_index1 ON tmm_work_type_item(work_type_code, activate_date);
CREATE INDEX tmm_work_type_pattern_index1 ON tmm_work_type_pattern(pattern_code, activate_date);
CREATE INDEX tmt_paid_holiday_index1 ON tmt_paid_holiday(personal_id, activate_date, acquisition_date);
CREATE INDEX tmt_stock_holiday_index1 ON tmt_stock_holiday(personal_id, activate_date, acquisition_date);
CREATE INDEX tmt_total_time_index1 ON tmt_total_time(calculation_year, calculation_month);
CREATE INDEX tmt_total_time_employee_index1 ON tmt_total_time_employee(personal_id, calculation_year, calculation_month);
CREATE INDEX tmt_attendance_index1 ON tmt_attendance(personal_id, work_date);
CREATE INDEX tmm_paid_holiday_proportionally_index1 ON tmm_paid_holiday_proportionally(paid_holiday_code, activate_date, prescribed_weekly_working_days, continuous_service_terms_counting_from_the_employment_day);
CREATE INDEX tmt_paid_holiday_grant_index1 ON tmt_paid_holiday_grant(personal_id, grant_date);