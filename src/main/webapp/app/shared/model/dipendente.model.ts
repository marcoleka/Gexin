import { ILog } from 'app/shared/model//log.model';

export interface IDipendente {
    id?: number;
    name?: string;
    surname?: string;
    ruolo?: string;
    codiceFiscale?: string;
    reparto?: string;
    numeroScheda?: string;
    schedaPersa?: boolean;
    accessoId?: number;
    logs?: ILog[];
}

export class Dipendente implements IDipendente {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public ruolo?: string,
        public codiceFiscale?: string,
        public reparto?: string,
        public numeroScheda?: string,
        public schedaPersa?: boolean,
        public accessoId?: number,
        public logs?: ILog[]
    ) {
        this.schedaPersa = this.schedaPersa || false;
    }
}
