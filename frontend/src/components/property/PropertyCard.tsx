import { FaHeart, FaRegHeart } from "react-icons/fa";
import "@/styles/property/property.css";
import { Link } from "react-router-dom";
import { MdDelete, MdEdit } from "react-icons/md";

interface Props {
  propertyId: string;
  title: string;
  address: string;
  price: number;
  status: string;
  totalNights?: number;
  image: string[];
  isManageMode: boolean;
  isFavorite: boolean;
  onFavorite?: (id: string) => void;
  onDelete?: (id: string) => void;
  onEdit?: (id: string) => void;
}

const PropertyCard = ({
  propertyId,
  title,
  address,
  image,
  price,
  status = "AVAILABLE",
  totalNights = 1,
  isManageMode,
  isFavorite = false,
  onFavorite,
  onDelete,
  onEdit,
}: Props) => {
  const formatted = price.toLocaleString("en-US", {
    style: "currency",
    currency: "PHP",
  });

  const cardContent = (
    <div key={propertyId} className="property-card">
      <img src={image[0]} alt={title} />

      <div className="property-info">
        <h3>{title}</h3>
        <h4>{address}</h4>
        <h4>₱{price.toLocaleString()}</h4>
        <p className={`status ${status.toLowerCase()}`}>
          {formatted} for {totalNights} {totalNights > 1 ? "nights" : "night"}
        </p>
      </div>

      {isManageMode && (
        <div className="property-actions">
          <button
            className="property-actions-buttons edit-btn"
            onClick={() => onEdit && onEdit(propertyId)}
          >
            <MdEdit />
          </button>
          <button
            className="property-actions-buttons delete-btn"
            onClick={() => onDelete && onDelete(propertyId)}
          >
            <MdDelete />
          </button>
        </div>
      )}
    </div>
  );

  return (
    <div className="property-card-container">
      {isManageMode ? (
        cardContent
      ) : (
        <Link to={`/property/${propertyId}`} className="property-link">
          <button
            onClick={(e) => {
              e.stopPropagation();
              e.preventDefault();
              onFavorite?.(propertyId);
            }}
            className="favorite"
          >
            {isFavorite ? <FaHeart size={25} /> : <FaRegHeart size={25} />}
          </button>
          {cardContent}
        </Link>
      )}
    </div>
  );
};

export default PropertyCard;
