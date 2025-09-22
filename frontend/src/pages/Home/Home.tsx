import PropertyCard from "@/components/property/PropertyCard";
import { useState } from "react";

const Home = () => {
  const [isFavorite, setIsFavorite] = useState(false);

  return (
    <div className="bg-black">
      <PropertyCard
        isFavorite={isFavorite}
        setIsFavorite={(favorite: boolean) => setIsFavorite(favorite)}
        title={"Title"}
        price={100}
        totalNights={2}
        imageUrl={
          "https://a0.muscache.com/im/pictures/0d8a93b3-f80d-4fc1-85ee-1f75dafed692.jpg?im_w=1200"
        }
        id="asdasd"
      />
    </div>
  );
};

export default Home;
