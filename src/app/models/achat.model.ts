export interface Achat {
  id?: number;
  produitId: number;
  fournisseurId: number;
  quantite: number;
  prixUnitaire: number;
  montantTotal?: number;
  dateAchat?: Date;
  nomProduit?: string;
  nomFournisseur?: string;
}