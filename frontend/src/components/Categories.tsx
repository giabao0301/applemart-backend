import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import Category from "../types/category";

const Categories = () => {
  const [categories, setCategories] = useState<Array<Category>>([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/categories"
        );

        if (response.status === 200) {
          setCategories(response.data);
        } else {
          throw new Error("Failed to fetch categories");
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchCategories();
  }, []);

  return (
    <div className="w-full pb-[62px] inline-flex pt-3 align-top justify-between">
      {categories.map((category: Category) => (
        <div className="flex flex-col gap-3" key={category.id}>
          <img
            className="block mx-auto my-0 w-auto h-[78px]"
            src={category.imageUrl}
            alt=""
          />
          <Link
            className="block text-base text-center text-primary hover:underline"
            to={`/${category.id}`}
          >
            {category.name}
          </Link>
        </div>
      ))}
    </div>
  );
};

export default Categories;
