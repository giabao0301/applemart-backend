import { Form } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form";
import exclamationIcon from "../assets/exclamation.svg";
import { Link } from "react-router-dom";
import { useEffect } from "react";

type userInputs = {
  email: string;
  password: string;
};

const LoginForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<userInputs>();
  const onSubmit: SubmitHandler<userInputs> = (data) => console.log(data);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <Form className="mt-24" onSubmit={handleSubmit(onSubmit)}>
      <h1 className="text-3xl text-center font-bold pt-8">Đăng nhập</h1>
      <div className="max-w-[1200px] m-auto w-4/5 pt-8 pb-5 px-0 border-b border-solid border-[#e7e7e8]">
        <div className="flex flex-col justify-around max-w-[460px] mx-auto gap-6 mb-4">
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
            placeholder="Email"
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
        <div className="text-center">
          Chưa có tài khoản?
          <Link to="/account" className="text-[#0071e3] cursor-pointer ml-2">
            Đăng ký
          </Link>
        </div>
      </div>
      <div className="min-h-10 max-w-full m-auto text-center mt-4">
        <button className="text-[18px] py-1 px-[15px] inline-block bg-gradient-to-b from-[#42a1ec] to-[#0070c9] text-white rounded hover:opacity-80">
          Đăng nhập
        </button>
      </div>
    </Form>
  );
};

export default LoginForm;
