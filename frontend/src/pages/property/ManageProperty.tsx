import { Link } from "react-router-dom";
import { MdEdit, MdDelete } from "react-icons/md";
import "@/styles/property/manageProperty.css";
import { useEffect, useState } from "react";
import { getMyProperties } from "@/utils/property";
import { PropertyTypesView } from "@/pages/property/Propertytypes";

const ManageProperty = () => {
  const [properties, setProperties] = useState<PropertyTypesView[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  const handleDelete = (propertyId: string) => {
    if (!propertyId) {
      throw new Error("Property ID is missing in search params");
    }
    setProperties((prev) => prev.filter((p) => p.id !== propertyId));
  };

  useEffect(() => {
    const fetchProperties = async () => {
      const res = await getMyProperties();

      if (res.success) {
        setProperties(res?.properties ?? []);
        setLoading(false);
      }
    };

    fetchProperties();
  }, []);

  if (loading) {
    return (
      <div>
        <h1>Loading.........</h1>
      </div>
    );
  }

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
        {properties.length > 0 ? (
          properties.map((property) => (
            <div key={property.id} className="property-card">
              {property.image.map((image) => (
                <img src={image} alt={property.title} />
              ))}

              <div className="property-info">
                <h3>{property.title}</h3>
                <h4>{property.address}</h4>
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
          ))
        ) : (
          <div>
            <h1>No properties</h1>
          </div>
        )}
      </div>
    </div>
  );
};

export default ManageProperty;
