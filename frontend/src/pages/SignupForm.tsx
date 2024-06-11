import React, { useState } from "react";
import { Form } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form";
import exclamationIcon from "../../src/assets/exclamation.svg";
import { Link } from "react-router-dom";

type userInputs = {
  lastName: string;
  firstName: string;
  dateOfBirth: string;
  province: string;
  email: string;
  password: string;
  confirmedPassword: string;
  phoneNumber: string;
};

const SignupForm = () => {
  const PROVINCES = [
    "Hồ Chí Minh",
    "Hà Nội",
    "Đà Nẵng",
    "An Giang",
    "Bà Rịa - Vũng Tàu",
    "Bắc Giang",
    "Bắc Kạn",
    "Bạc Liêu",
    "Bắc Ninh",
    "Bến Tre",
    "Bình Định",
    "Bình Dương",
    "Bình Phước",
    "Bình Thuận",
    "Cà Mau",
    "Cần Thơ",
    "Cao Bằng",
    "Đắk Lắk",
    "Đắk Nông",
    "Điện Biên",
    "Đồng Nai",
    "Đồng Tháp",
    "Gia Lai",
    "Hà Giang",
    "Hà Nam",
    "Hà Tĩnh",
    "Hải Dương",
    "Hải Phòng",
    "Hậu Giang",
    "Hòa Bình",
    "Hưng Yên",
    "Khánh Hòa",
    "Kiên Giang",
    "Kon Tum",
    "Lai Châu",
    "Lâm Đồng",
    "Lạng Sơn",
    "Lào Cai",
    "Long An",
    "Nam Định",
    "Nghệ An",
    "Ninh Bình",
    "Ninh Thuận",
    "Phú Thọ",
    "Phú Yên",
    "Quảng Bình",
    "Quảng Nam",
    "Quảng Ngãi",
    "Quảng Ninh",
    "Quảng Trị",
    "Sóc Trăng",
    "Sơn La",
    "Tây Ninh",
    "Thái Bình",
    "Thái Nguyên",
    "Thanh Hóa",
    "Thừa Thiên Huế",
    "Tiền Giang",
    "Trà Vinh",
    "Tuyên Quang",
    "Vĩnh Long",
    "Vĩnh Phúc",
    "Yên Bái",
  ];

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<userInputs>();
  const onSubmit: SubmitHandler<userInputs> = (data) => console.log(data);

  const [user, setUser] = useState<userInputs>({
    lastName: "",
    firstName: "",
    dateOfBirth: "",
    province: "",
    email: "",
    password: "",
    phoneNumber: "",
    confirmedPassword: "",
  });

  return (
    <Form className="mt-18" onSubmit={handleSubmit(onSubmit)}>
      <h1 className="text-3xl text-center font-bold pt-8">Tạo tài khoản</h1>
      <div className="max-w-[1200px] m-auto w-4/5 pt-8 pb-5 px-0 border-b border-solid border-[#e7e7e8]">
        <div className="flex justify-around max-w-[460px] mx-auto gap-6 mb-4">
          <div>
            <input
              {...register("lastName", { required: true })}
              className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
                errors.lastName
                  ? "border-error text-error bg-[#fef0f0] outline-error"
                  : "border-[#d6d6d6] outline-[#0070c9]"
              }`}
              placeholder="Họ"
            />
            {errors.lastName && (
              <div className="flex items-center gap-1 mt-1">
                <div>
                  <img src={exclamationIcon} alt="" width={12} height={12} />
                </div>
                <span className="text-error text-[12px] mt-[1px]">
                  Vui lòng nhập họ.
                </span>
              </div>
            )}
          </div>

          <div>
            <input
              {...register("firstName", { required: true })}
              className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
                errors.firstName
                  ? "border-error text-error bg-[#fef0f0] outline-error"
                  : "border-[#d6d6d6] outline-[#0070c9]"
              }`}
              placeholder="Tên"
            />
            {errors.firstName && (
              <div className="flex items-center gap-1 mt-1">
                <div>
                  <img src={exclamationIcon} alt="" width={12} height={12} />
                </div>
                <span className="text-error text-[12px] mt-[1px]">
                  Vui lòng nhập tên.
                </span>
              </div>
            )}
          </div>
        </div>

        <div className="max-w-[460px] mx-auto mb-4">
          <h3 className="text-[14px] text-secondary leading-6 font-semibold pb-1 tracking-wider">
            THÀNH PHỐ
          </h3>
          <select
            name="province"
            id="province"
            className="pr-7 pl-4 min-h-8 w-full text-left text-[17px] h-14 leading-6 border border-solid border-[#d6d6d6] rounded focus:outline outline-[#0070c9]"
            onChange={(e) => setUser({ ...user, province: e.target.value })}
          >
            {PROVINCES.map((province, index) => (
              <option key={index} value={province}>
                {province}
              </option>
            ))}
          </select>
        </div>

        <div className="max-w-[460px] mx-auto mb-4">
          <input
            {...register("dateOfBirth", {
              required: true,
              pattern: {
                value:
                  /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/,
                message: "Ngày sinh không hợp lệ.",
              },
              validate: (value: string) =>
                !isNaN(new Date(value).getTime()) || "Ngày sinh không hợp lệ.",
            })}
            className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
              errors.dateOfBirth
                ? "border-error text-error bg-[#fef0f0] outline-error"
                : "border-[#d6d6d6] outline-[#0070c9]"
            }`}
            placeholder="Ngày sinh"
            onFocus={(e) => {
              e.target.placeholder = "NN/TT/NNNN";
            }}
            onBlur={(e) => {
              e.target.placeholder = "Ngày sinh";
            }}
          />
          {errors.dateOfBirth && (
            <div className="flex items-center gap-1 mt-1">
              <div>
                <img src={exclamationIcon} alt="" width={12} height={12} />
              </div>
              <span className="text-error text-[12px] mt-[1px]">
                {errors.dateOfBirth.message || "Vui lòng nhập ngày sinh."}
              </span>
            </div>
          )}
        </div>
      </div>

      <div className="max-w-[1200px] m-auto w-4/5 pt-8 pb-5 px-0 border-b border-solid border-[#e7e7e8]">
        <div className="max-w-[460px] mx-auto mb-4">
          <input
            {...register("email", {
              required: true,
              pattern: {
                value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                message: "Nhập địa chỉ email hợp lệ.",
              },
            })}
            className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
              errors.email
                ? "border-error text-error bg-[#fef0f0] outline-error"
                : "border-[#d6d6d6] outline-[#0070c9]"
            }`}
            placeholder="name@example.com"
          />
          {errors.email && (
            <div className="flex items-center gap-1 mt-1">
              <div>
                <img src={exclamationIcon} alt="" width={12} height={12} />
              </div>
              <span className="text-error text-[12px] mt-[1px]">
                {errors.email.message || "Vui lòng nhập email."}
              </span>
            </div>
          )}
        </div>
        <div className="max-w-[460px] mx-auto mb-4">
          <input
            type="password"
            {...register("password", {
              required: true,
              pattern: {
                value:
                  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/,
                message:
                  "Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.",
              },
            })}
            className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
              errors.password
                ? "border-error text-error bg-[#fef0f0] outline-error"
                : "border-[#d6d6d6] outline-[#0070c9]"
            }`}
            placeholder="Mật khẩu"
          />
          {errors.password && (
            <div className="flex items-center gap-1 mt-1">
              <div>
                <img src={exclamationIcon} alt="" width={12} height={12} />
              </div>
              <span className="text-error text-[12px] mt-[1px]">
                {errors.password.message || "Vui lòng nhập mật khẩu."}
              </span>
            </div>
          )}
        </div>
        <div className="max-w-[460px] mx-auto mb-4">
          <input
            type="password"
            {...register("confirmedPassword", {
              required: true,
              validate: (value) =>
                value === watch("password") || "Mật khẩu không khớp.",
            })}
            className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
              errors.confirmedPassword
                ? "border-error text-error bg-[#fef0f0] outline-error"
                : "border-[#d6d6d6] outline-[#0070c9]"
            }`}
            placeholder="Xác nhận mật khẩu"
          />
          {errors.confirmedPassword && (
            <div className="flex items-center gap-1 mt-1">
              <div>
                <img src={exclamationIcon} alt="" width={12} height={12} />
              </div>
              <span className="text-error text-[12px] mt-[1px]">
                {errors.confirmedPassword.message ||
                  "Vui lòng xác nhận mật khẩu."}
              </span>
            </div>
          )}
        </div>
        <div className="max-w-[460px] mx-auto mb-4">
          <input
            {...register("phoneNumber", {
              required: true,
              pattern: {
                value: /(84|0[3|5|7|8|9])+([0-9]{8})\b/g,
                message: "Nhập số điện thoại hợp lệ.",
              },
            })}
            className={`text-[17px] leading-5 inline-block w-full h-14 py-[18px] px-4 border border-solid  align-middle rounded focus:outline ${
              errors.phoneNumber
                ? "border-error text-error bg-[#fef0f0] outline-error"
                : "border-[#d6d6d6] outline-[#0070c9]"
            }`}
            placeholder="Số điện thoại"
          />
          {errors.phoneNumber && (
            <div className="flex items-center gap-1 mt-1">
              <div>
                <img src={exclamationIcon} alt="" width={12} height={12} />
              </div>
              <span className="text-error text-[12px] mt-[1px]">
                {errors.phoneNumber.message || "Vui lòng nhập số điện thoại."}
              </span>
            </div>
          )}
        </div>
        <div className="text-center">
          Đã có tài khoản?
          <Link to="/login" className="text-[#0071e3] cursor-pointer ml-2">
            Đăng nhập
          </Link>
        </div>
      </div>
      <div className="min-h-10 max-w-full m-auto text-center mt-4">
        <button className="text-[18px] py-1 px-[15px] inline-block bg-gradient-to-b from-[#42a1ec] to-[#0070c9] text-white rounded hover:opacity-80">
          Đăng ký
        </button>
      </div>
    </Form>
  );
};

export default SignupForm;
