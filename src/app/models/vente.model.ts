export interface Vente {
  id?: number;
  produitId: number;
  clientId: number;
  quantite: number;
  prixUnitaire: number;
  montantTotal?: number;
  dateVente?: Date;
  nomProduit?: string;
  nomClient?: string;
  prenomClient?: string;
}