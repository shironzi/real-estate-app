import { Link } from "react-router-dom";
import { MdEdit, MdDelete } from "react-icons/md";
import "@/styles/property/manageProperty.css";
import { useState } from "react";

type Property = {
  id: number;
  title: string;
  location: string;
  price: number;
  status: "Available" | "Occupied" | "Pending";
  type: string;
  image: string;
};

const ManageProperty = () => {
  const [properties, setProperties] = useState<Property[]>([
    {
      id: 1,
      title: "Modern Apartment",
      location: "Manila",
      price: 25000,
      status: "Available",
      type: "Apartment",
      image:
        "https://prod.rockmedialibrary.com/api/public/content/ff061825fa8e44bf8108de5c786c0062?v=4c4f7c7a",
    },
    {
      id: 2,
      title: "Beach House",
      location: "Cebu",
      price: 120000,
      status: "Occupied",
      type: "House",
      image:
        "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800",
    },
  ]);

  const handleDelete = (id: number) => {
    setProperties((prev) => prev.filter((p) => p.id !== id));
  };

  return (
    <div className="manage-property">
      {/* Header Section */}
      <div className="manage-header">
        <h2>Manage Properties</h2>
        <Link to={"/property/form"} className="add-btn">
          Add Property
        </Link>
      </div>

      {/* Filters */}
      <div className="filters">
        <label>
          Type
          <select>
            <option value="">All</option>
            <option value="Apartment">Apartment</option>
            <option value="House">House</option>
          </select>
        </label>
        <label>
          Location
          <select>
            <option value="">All</option>
            <option value="Manila">Manila</option>
            <option value="Cebu">Cebu</option>
          </select>
        </label>
      </div>

      {/* Property List */}
      <div className="manage-property-list">
        {properties.map((property) => (
          <div key={property.id} className="property-card">
            <img
              src={property.image}
              alt={property.title}
              width={120}
              height={100}
            />
            <div className="property-info">
              <h3>{property.title}</h3>
              <h4>{property.location}</h4>
              <h4>₱{property.price.toLocaleString()}</h4>
              <h4 className={`status ${property.status.toLowerCase()}`}>
                {property.status}
              </h4>
            </div>
            <div className="property-actions">
              <button className="property-actions-buttons edit-btn">
                <MdEdit />
              </button>
              <button
                className="property-actions-buttons delete-btn"
                onClick={() => handleDelete(property.id)}
              >
                <MdDelete />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ManageProperty;
