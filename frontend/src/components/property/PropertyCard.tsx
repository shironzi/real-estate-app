import { FaHeart, FaRegHeart } from "react-icons/fa";
import "@/styles/property/property.css";
import { Link } from "react-router-dom";

interface Props {
  isFavorite: boolean;
  setIsFavorite: (favorite: boolean) => void;
  title: string;
  price: number;
  totalNights: number;
  imageUrl: string;
  id: string;
}

const PropertyCard = ({
  isFavorite,
  setIsFavorite,
  title,
  price,
  totalNights,
  imageUrl,
  id,
}: Props) => {
  const handleFavorite = () => {
    setIsFavorite(!isFavorite);
  };

  const formatted = price.toLocaleString("en-US", {
    style: "currency",
    currency: "PHP",
  });

  return (
    <Link to={`/property/${id}`} className="property-card-container">
      <img src={imageUrl} alt="propertyImage" />
      <button onClick={handleFavorite} className="favorite">
        {isFavorite ? <FaHeart size={25} /> : <FaRegHeart size={25} />}
      </button>
      <h4>{title}</h4>
      <p>
        {formatted} for {totalNights} {totalNights > 1 ? "nights" : "night"}
      </p>
    </Link>
  );
};

export default PropertyCard;
