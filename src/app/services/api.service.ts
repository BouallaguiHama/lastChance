import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Produit } from '../models/produit.model';
import { Client } from '../models/client.model';
import { Fournisseur } from '../models/fournisseur.model';
import { Achat } from '../models/achat.model';
import { Vente } from '../models/vente.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Une erreur est survenue';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Erreur ${error.status}: ${error.error?.message || error.message}`;
    }
    return throwError(() => errorMessage);
  }

  // Produits
  getProduits(): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.baseUrl}/produits`)
      .pipe(catchError(this.handleError));
  }

  getProduit(id: number): Observable<Produit> {
    return this.http.get<Produit>(`${this.baseUrl}/produits/${id}`)
      .pipe(catchError(this.handleError));
  }

  createProduit(produit: Produit): Observable<Produit> {
    return this.http.post<Produit>(`${this.baseUrl}/produits`, produit)
      .pipe(catchError(this.handleError));
  }

  updateProduit(id: number, produit: Produit): Observable<Produit> {
    return this.http.put<Produit>(`${this.baseUrl}/produits/${id}`, produit)
      .pipe(catchError(this.handleError));
  }

  deleteProduit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/produits/${id}`)
      .pipe(catchError(this.handleError));
  }

  getProduitsStockFaible(): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.baseUrl}/produits/stock-faible`)
      .pipe(catchError(this.handleError));
  }

  // Clients
  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.baseUrl}/clients`)
      .pipe(catchError(this.handleError));
  }

  getClient(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.baseUrl}/clients/${id}`)
      .pipe(catchError(this.handleError));
  }

  createClient(client: Client): Observable<Client> {
    return this.http.post<Client>(`${this.baseUrl}/clients`, client)
      .pipe(catchError(this.handleError));
  }

  updateClient(id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(`${this.baseUrl}/clients/${id}`, client)
      .pipe(catchError(this.handleError));
  }

  deleteClient(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/clients/${id}`)
      .pipe(catchError(this.handleError));
  }

  // Fournisseurs
  getFournisseurs(): Observable<Fournisseur[]> {
    return this.http.get<Fournisseur[]>(`${this.baseUrl}/fournisseurs`)
      .pipe(catchError(this.handleError));
  }

  getFournisseur(id: number): Observable<Fournisseur> {
    return this.http.get<Fournisseur>(`${this.baseUrl}/fournisseurs/${id}`)
      .pipe(catchError(this.handleError));
  }

  createFournisseur(fournisseur: Fournisseur): Observable<Fournisseur> {
    return this.http.post<Fournisseur>(`${this.baseUrl}/fournisseurs`, fournisseur)
      .pipe(catchError(this.handleError));
  }

  updateFournisseur(id: number, fournisseur: Fournisseur): Observable<Fournisseur> {
    return this.http.put<Fournisseur>(`${this.baseUrl}/fournisseurs/${id}`, fournisseur)
      .pipe(catchError(this.handleError));
  }

  deleteFournisseur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/fournisseurs/${id}`)
      .pipe(catchError(this.handleError));
  }

  // Achats
  getAchats(): Observable<Achat[]> {
    return this.http.get<Achat[]>(`${this.baseUrl}/achats`)
      .pipe(catchError(this.handleError));
  }

  createAchat(achat: Achat): Observable<Achat> {
    return this.http.post<Achat>(`${this.baseUrl}/achats`, achat)
      .pipe(catchError(this.handleError));
  }

  // Ventes
  getVentes(): Observable<Vente[]> {
    return this.http.get<Vente[]>(`${this.baseUrl}/ventes`)
      .pipe(catchError(this.handleError));
  }

  createVente(vente: Vente): Observable<Vente> {
    return this.http.post<Vente>(`${this.baseUrl}/ventes`, vente)
      .pipe(catchError(this.handleError));
  }
}