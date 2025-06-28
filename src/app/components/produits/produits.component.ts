import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { Produit } from '../../models/produit.model';

@Component({
  selector: 'app-produits',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  template: `
    <div class="container mt-4">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Gestion des Produits</h1>
        <button (click)="showForm = !showForm" class="btn btn-primary">
          <span class="material-icons">add</span>
          Nouveau Produit
        </button>
      </div>

      <!-- Formulaire -->
      <div *ngIf="showForm" class="card mb-4">
        <h3>{{ editingProduit?.id ? 'Modifier' : 'Ajouter' }} un Produit</h3>
        <form (ngSubmit)="saveProduit()" #produitForm="ngForm">
          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Nom *</label>
              <input type="text" class="form-control" 
                     [(ngModel)]="currentProduit.nom" 
                     name="nom" required>
            </div>
            <div class="form-group">
              <label class="form-label">Prix Unitaire *</label>
              <input type="number" step="0.01" class="form-control" 
                     [(ngModel)]="currentProduit.prixUnitaire" 
                     name="prixUnitaire" required>
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Prix de Vente *</label>
              <input type="number" step="0.01" class="form-control" 
                     [(ngModel)]="currentProduit.prixVente" 
                     name="prixVente" required>
            </div>
            <div class="form-group">
              <label class="form-label">Quantité en Stock</label>
              <input type="number" class="form-control" 
                     [(ngModel)]="currentProduit.quantiteStock" 
                     name="quantiteStock">
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label class="form-label">Seuil Minimum</label>
              <input type="number" class="form-control" 
                     [(ngModel)]="currentProduit.seuilMinimum" 
                     name="seuilMinimum">
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Description</label>
            <textarea class="form-control" rows="3"
                      [(ngModel)]="currentProduit.description" 
                      name="description"></textarea>
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-success" [disabled]="!produitForm.valid">
              {{ editingProduit?.id ? 'Modifier' : 'Ajouter' }}
            </button>
            <button type="button" class="btn btn-outline" (click)="cancelEdit()">
              Annuler
            </button>
          </div>
        </form>
      </div>

      <!-- Alerte -->
      <div *ngIf="message" class="alert" [class]="'alert-' + messageType">
        {{ message }}
      </div>

      <!-- Liste des produits -->
      <div class="card">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h3>Liste des Produits ({{ produits.length }})</h3>
          <button (click)="loadProduits()" class="btn btn-outline">
            <span class="material-icons">refresh</span>
            Actualiser
          </button>
        </div>

        <div *ngIf="loading" class="loading">
          <div class="spinner"></div>
        </div>

        <div *ngIf="!loading && produits.length === 0" class="text-center mt-4">
          <p>Aucun produit trouvé. Commencez par ajouter votre premier produit !</p>
        </div>

        <table *ngIf="!loading && produits.length > 0" class="table">
          <thead>
            <tr>
              <th>Nom</th>
              <th>Stock</th>
              <th>Prix Unitaire</th>
              <th>Prix Vente</th>
              <th>Seuil Min.</th>
              <th>Statut</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let produit of produits">
              <td>
                <div>
                  <strong>{{ produit.nom }}</strong>
                  <div *ngIf="produit.description" class="text-muted">{{ produit.description }}</div>
                </div>
              </td>
              <td>
                <span class="badge" 
                      [class]="getStockBadgeClass(produit)">
                  {{ produit.quantiteStock }}
                </span>
              </td>
              <td>{{ produit.prixUnitaire | currency:'EUR':'symbol':'1.2-2' }}</td>
              <td>{{ produit.prixVente | currency:'EUR':'symbol':'1.2-2' }}</td>
              <td>{{ produit.seuilMinimum }}</td>
              <td>
                <span class="badge" [class]="getStockStatusClass(produit)">
                  {{ getStockStatus(produit) }}
                </span>
              </td>
              <td>
                <div class="action-buttons">
                  <button (click)="editProduit(produit)" class="btn btn-sm btn-outline">
                    <span class="material-icons">edit</span>
                  </button>
                  <button (click)="deleteProduit(produit)" class="btn btn-sm btn-danger">
                    <span class="material-icons">delete</span>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `,
  styles: [`
    .form-row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
    }

    .form-actions {
      display: flex;
      gap: 12px;
      margin-top: 20px;
    }

    .action-buttons {
      display: flex;
      gap: 8px;
    }

    .btn-sm {
      padding: 6px 12px;
      font-size: 14px;
    }

    .text-muted {
      color: #6b7280;
      font-size: 14px;
    }

    @media (max-width: 768px) {
      .form-row {
        grid-template-columns: 1fr;
      }
      
      .table {
        font-size: 14px;
      }
      
      .action-buttons {
        flex-direction: column;
      }
    }
  `]
})
export class ProduitsComponent implements OnInit {
  produits: Produit[] = [];
  currentProduit: Produit = this.initProduit();
  editingProduit: Produit | null = null;
  showForm = false;
  loading = false;
  message = '';
  messageType = 'success';

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.loadProduits();
  }

  initProduit(): Produit {
    return {
      nom: '',
      description: '',
      quantiteStock: 0,
      prixUnitaire: 0,
      prixVente: 0,
      seuilMinimum: 5
    };
  }

  loadProduits() {
    this.loading = true;
    this.apiService.getProduits().subscribe({
      next: (produits) => {
        this.produits = produits;
        this.loading = false;
      },
      error: (error) => {
        this.showMessage('Erreur lors du chargement des produits: ' + error, 'danger');
        this.loading = false;
      }
    });
  }

  saveProduit() {
    if (this.editingProduit?.id) {
      this.apiService.updateProduit(this.editingProduit.id, this.currentProduit).subscribe({
        next: () => {
          this.showMessage('Produit modifié avec succès', 'success');
          this.loadProduits();
          this.cancelEdit();
        },
        error: (error) => this.showMessage('Erreur lors de la modification: ' + error, 'danger')
      });
    } else {
      this.apiService.createProduit(this.currentProduit).subscribe({
        next: () => {
          this.showMessage('Produit ajouté avec succès', 'success');
          this.loadProduits();
          this.cancelEdit();
        },
        error: (error) => this.showMessage('Erreur lors de l\'ajout: ' + error, 'danger')
      });
    }
  }

  editProduit(produit: Produit) {
    this.editingProduit = produit;
    this.currentProduit = { ...produit };
    this.showForm = true;
  }

  deleteProduit(produit: Produit) {
    if (confirm(`Êtes-vous sûr de vouloir supprimer le produit "${produit.nom}" ?`)) {
      this.apiService.deleteProduit(produit.id!).subscribe({
        next: () => {
          this.showMessage('Produit supprimé avec succès', 'success');
          this.loadProduits();
        },
        error: (error) => this.showMessage('Erreur lors de la suppression: ' + error, 'danger')
      });
    }
  }

  cancelEdit() {
    this.editingProduit = null;
    this.currentProduit = this.initProduit();
    this.showForm = false;
  }

  getStockBadgeClass(produit: Produit): string {
    if (produit.quantiteStock === 0) return 'badge-danger';
    if (produit.quantiteStock <= produit.seuilMinimum) return 'badge-warning';
    return 'badge-success';
  }

  getStockStatus(produit: Produit): string {
    if (produit.quantiteStock === 0) return 'Rupture';
    if (produit.quantiteStock <= produit.seuilMinimum) return 'Stock Faible';
    return 'Disponible';
  }

  getStockStatusClass(produit: Produit): string {
    if (produit.quantiteStock === 0) return 'badge-danger';
    if (produit.quantiteStock <= produit.seuilMinimum) return 'badge-warning';
    return 'badge-success';
  }

  showMessage(message: string, type: string) {
    this.message = message;
    this.messageType = type;
    setTimeout(() => this.message = '', 5000);
  }
}