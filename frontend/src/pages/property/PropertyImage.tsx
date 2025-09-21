import { FaImage } from "react-icons/fa6";

import "@/styles/property/propertyImage.css";
import { useProperty } from "@/context/PropertyContext";
import { ChangeEvent } from "react";

const PropertyImages = () => {
  const { data, setData } = useProperty();

  const handleAddImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const image = e.target.files[0];
      setData((prev) => ({
        ...prev,
        images: [...prev.images, image],
      }));
    }
  };

  const handleRemoveImage = (index: number) => {
    const newFiles = data.images.filter((_, i) => i !== index);
    setData((prev) => ({ ...prev, images: newFiles }));
  };

  const handleEditImage = (
    e: React.ChangeEvent<HTMLInputElement>,
    index: number
  ) => {
    if (e.target.files && e.target.files[0]) {
      const updatedFile = e.target.files[0];
      setData((prev) => {
        const updatedImages = [...prev.images];
        updatedImages[index] = updatedFile;
        return { ...prev, images: updatedImages };
      });
    }
  };

  return (
    <div className="property-image-container">
      <div className="image-grid">
        {data.images.map((file, index) => (
          <div key={index} className="image-card">
            <img src={URL.createObjectURL(file)} alt={`preview-${index}`} />
            <div className="image-actions">
              <button onClick={() => handleRemoveImage(index)}>🗑️</button>
              <label>
                ✏️
                <input
                  type="file"
                  accept="image/*"
                  hidden
                  onChange={(e) => handleEditImage(e, index)}
                />
              </label>
            </div>
          </div>
        ))}

        <label className="add-image-card">
          <FaImage size={50} color="#fff" />
          <h4>+ Add Image</h4>
          <input
            type="file"
            multiple
            accept="image/*"
            hidden
            onChange={handleAddImage}
          />
        </label>
      </div>

      <div className="property-button-container">
        <button>
          <h3>Next</h3>
        </button>
      </div>
    </div>
  );
};

export default PropertyImages;
