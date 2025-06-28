export interface Produit {
  id?: number;
  nom: string;
  description?: string;
  quantiteStock: number;
  prixUnitaire: number;
  prixVente: number;
  seuilMinimum: number;
  dateCreation?: Date;
  dateModification?: Date;
}